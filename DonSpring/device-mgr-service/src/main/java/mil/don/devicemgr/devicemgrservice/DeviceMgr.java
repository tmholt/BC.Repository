package mil.don.devicemgr.devicemgrservice;


import com.netflix.appinfo.ApplicationInfoManager;

import mil.don.common.devices.DeviceCapability;
import mil.don.common.devices.DeviceEntity;
import mil.don.common.interfaces.IDevice;
import mil.don.common.logging.Priority;
import mil.don.common.status.ServiceStatus;
import mil.don.proxies.LoggingProxy;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;


@Service
public class DeviceMgr
        extends HashMap<String, IDevice>
        implements IDeviceMgr { // HealthIndicator


    // this is the topic name that service status events will be pushed to
    public static final String ROUTING_KEY = "status.service";

    // the proxy to our logging service
    @Autowired
    private LoggingProxy _logging;

    // eureka
    @Autowired
    private ApplicationInfoManager _appInfo;



    private RabbitTemplate _rabbitTemplate;
    private Exchange _exchange;


    // this constructor is called via a bean defined in configuration
    @Autowired
    public DeviceMgr(RabbitTemplate rabbitTemplate, Exchange exchange) {
        _rabbitTemplate = rabbitTemplate;
        _exchange = exchange;

        //publishStatusAtFixedRate();
    }

    // this is an example of adding to the meta-data available from eureka for this service.
    // in this case, this is a terrible example, as we are getting a number that isn't available yet. :)
    @PostConstruct
    public void initialize() {
        Map<String, String> mapping = _appInfo.getInfo().getMetadata();
        mapping.put("num_devices", String.valueOf(this.size()));
    }

    // ApplicationEvents are for internal (in-process) event support
    /*
    private ScheduledExecutorService _statusPublisher;

    private void publishStatusAtFixedRate() {
        _statusPublisher = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            ServiceStatus status = new ServiceStatus(getServiceName(), new Date(), true);
            StatusEvent sevent = new StatusEvent(getServiceName(), StatusType.SERVICE, status);
            _eventPublisher.publishEvent(sevent);
        };
        _statusPublisher.scheduleWithFixedDelay(task, 5, 5, TimeUnit.SECONDS);
    }
     */

    // we want to publish between uservices
    /* nope. although maybe an alternative to rabbitmq. don't really understand this stuff
    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "1"))
    public MessageSource<StatusEvent> publishStatusAtFixedRate()
    {
        return () -> {
            ServiceStatus status = new ServiceStatus(getServiceName(), new Date(), true);
            StatusEvent sevent = new StatusEvent(getServiceName(), StatusType.SERVICE, status);

            return MessageBuilder
                .withPayload(sevent)
                .build();
        };
    }
    */

    /* alternate to using @scheduled
    private void publishStatusAtFixedRate() {
        Runnable task = () -> sendServiceStatusEvent();
        _statusPublisher = Executors.newScheduledThreadPool(1);
        _statusPublisher.scheduleWithFixedDelay(task, 5, 5, TimeUnit.SECONDS);
    }
    */

    private static long _statusId = 1;

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    void sendServiceStatusEvent() {
        ServiceStatus status = new ServiceStatus(_statusId++, getServiceName(), new Date(), true);
        _rabbitTemplate.convertAndSend(_exchange.getName(), ROUTING_KEY, status );
        System.out.println("sent service status event: " + status.toString());
    }

    // part of IDonService or something like that
    public String getServiceName() {
        return "DeviceManager";
    }



    public boolean addDevice(IDevice device) {
        if ( this.containsKey(device.getId()) ) {
            return false;
        }
        this.put(device.getId(), device);

        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device added to configuration");
        return true;
    }

    public IDevice getById(String id) {

        if ( this.containsKey(id) ) {
            _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device lookup success: " + id);
            return this.get(id);
        }
        else {
            _logging.log(Priority.WARNING, "DeviceMgrService::DeviceMgr", "Request for bad device id");
            return null;
        }
    }

    public IDevice[] getByCapability(DeviceCapability cap) {

        // these 2 code blocks are the same - first with streams, second explicit

        List<IDevice> list1 = this.entrySet().stream()
            .map(entry -> entry.getValue())
            .filter(entity -> entity.getCapabilities().contains(cap))
            .collect(Collectors.toList());

        /*
        ArrayList<DeviceEntity> list2 = new ArrayList<>();
        for ( DeviceEntity entity : this.values() ) {
            List<DeviceCapability> capabilities = entity.getCapabilities();
            if ( capabilities.contains(cap) ) list2.add(entity);
        }
         */

        int count = list1.size();
        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device cap " + cap + " lookup found: " + count);
        return list1.toArray(new IDevice[count]);
    }

    public IDevice[] getAll() {
        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device list request: " + this.size());
        return this.values().toArray(new IDevice[this.size()]);
    }

    // region HealthIndicator implementation
    /*
    @Override
    public Health health() {
        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Health request received");

        return Health
                .up()
                .withDetail("device manager is happy", "smiley-face")
                .build();
    }
     */
    // endregion

}
