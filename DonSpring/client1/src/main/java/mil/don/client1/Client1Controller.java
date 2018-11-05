package mil.don.client1;



import mil.don.common.status.ServiceStatus;
import mil.don.proxies.DeviceMgrProxy;
import mil.don.proxies.LoggingProxy;
import mil.don.common.devices.DeviceEntity;
import mil.don.common.logging.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@RestController
public class Client1Controller {

    @Autowired
    private DiscoveryClient _discoveryClient;

    @Autowired
    private LoggingProxy _logging;

    @Autowired
    private DeviceMgrProxy _deviceMgr;

    @Autowired
    private Client1StatusListener _statusListener;


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
    public DeviceEntity[] testDeviceMgrService() {
	    return _deviceMgr.getAll();
    }

    @RequestMapping("/status-events")
    public ServiceStatus[] getRecentStatusEvents() {
        return _statusListener.getRecent();
    }




}
