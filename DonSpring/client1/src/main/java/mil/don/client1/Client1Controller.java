package mil.don.client1;



import mil.don.common.devices.DetectionMessage;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.interfaces.IDevice;
import mil.don.common.status.IStatusMessage;
import mil.don.proxies.DeviceMgrProxy;
import mil.don.proxies.LoggingProxy;
import mil.don.common.logging.Priority;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;


@RestController
public class Client1Controller {

    public static final String DEVICE_COMMAND_ROUTING_KEY = "device.command";


    @Autowired
    private DiscoveryClient _discoveryClient;

    @Autowired
    private LoggingProxy _logging;

    @Autowired
    private DeviceMgrProxy _deviceMgr;

    @Autowired
    private Client1StatusListener _statusListener;

    // rabbitmq template
    @Autowired
    private RabbitTemplate _rabbitTemplate;

    // this is the rabbitmq exchange that will contain all commands sent to devices
    private Exchange _commandExchange;



    @PostConstruct
    public void initialize() {
        initializeExchanges();
     }

    // TODO: where oh where to put this
    private void initializeExchanges() {
        _commandExchange = new FanoutExchange("device-commands");
    }

    void exchangeDeviceCommandEvent(DeviceCommandBase command) {
        _rabbitTemplate.convertAndSend(_commandExchange.getName(), DEVICE_COMMAND_ROUTING_KEY, command);
        System.out.println("sent device command event: " + command.toString());
    }


	@RequestMapping("/services/{serviceId}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String serviceId) {
        return _discoveryClient.getInstances(serviceId);
    }

    @RequestMapping("/services")
    public List<ServiceInstance> serviceInstances() {

	    List<ServiceInstance> instances = new ArrayList<>();

	    List<String> services = _discoveryClient.getServices();

        for ( String service : services) {
            instances.addAll(_discoveryClient.getInstances(service));
        }

        return instances;
    }

    @RequestMapping("/testlog/{message}")
    public String testLoggingService(@PathVariable String message) {
        _logging.log(Priority.INFO, "mil.don.client1", message);
        return "ok";
    }

    @RequestMapping("/testdevices")
    public IDevice[] testDeviceMgrService() {
	    return _deviceMgr.getAllDevices();
    }

    @RequestMapping("/status-events")
    public IStatusMessage[] getRecentStatusEvents() {
        return _statusListener.getRecentStatusEvents();
    }

    @RequestMapping("/detection-events")
    public DetectionMessage[] getRecentDetectionEvents() {
        return _statusListener.getRecentDetectionEvents();
    }

    @RequestMapping("/command/{deviceId}/{command}")
    public String sendCommand(@PathVariable String deviceId,
        @PathVariable String command) {

        DeviceCommandBase msg = new DeviceCommandBase()
            .setClientName("client1")
            .setClientToken("abcd1234")
            .setCommand(command)
            .setDeviceId(deviceId);

        // this is the rabbitmq command exchange way
        //exchangeDeviceCommandEvent(msg);

        // this is the direct devicemgr api way
        String ok = _deviceMgr.executeCommand(msg);
        return ok;
    }


}
