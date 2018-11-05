package mil.don.devicemgr.devicemgrservice;


import com.netflix.appinfo.ApplicationInfoManager;

import mil.don.common.devices.DeviceEntity;
import mil.don.common.devices.DeviceType;
import mil.don.common.logging.Priority;
import mil.don.common.status.ServiceStatus;
import mil.don.proxies.LoggingProxy;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;


@Service
public class DeviceMgr
        extends HashMap<String, DeviceEntity>
        implements HealthIndicator, IDeviceMgr {


    // this is the topic name that service status events will be pushed to
    public static final String ROUTING_KEY = "status.service";

    // the proxy to our logging service
    @Autowired
    private LoggingProxy _logging;

    @Autowired
    private ApplicationInfoManager _appInfo;



    private ScheduledExecutorService _statusPublisher;
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
        _rabbitTemplate.convertAndSend(_exchange.getName(), ROUTING_KEY, status ); // sevent
    }

    // part of IDonService or something like that
    public String getServiceName() {
        return "DeviceManager";
    }



    public boolean addDevice(DeviceEntity device) {
        if ( this.containsKey(device.getId()) ) {
            return false;
        }
        this.put(device.getId(), device);

        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device added to configuration");
        return true;
    }

    public DeviceEntity getById(String id) {

        if ( this.containsKey(id) ) {
            _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device lookup success: " + id);
            return this.get(id);
        }
        else {
            _logging.log(Priority.WARNING, "DeviceMgrService::DeviceMgr", "Request for bad device id");
            return null;
        }
    }

    public DeviceEntity[] getByType(DeviceType type) {
        ArrayList<DeviceEntity> list = new ArrayList<>();
        for ( DeviceEntity entity : this.values() ) {
            List<DeviceType> capabilities = entity.getCapabilities();
            if ( capabilities.contains(type) ) list.add(entity);
        }

        int count = list.size();
        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device type " + type + " lookup found: " + count);
        return list.toArray(new DeviceEntity[count]);
    }

    public DeviceEntity[] getAll() {
        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device list request: " + this.size());
        return this.values().toArray(new DeviceEntity[this.size()]);
    }

    // region HealthIndicator implementation

    @Override
    public Health health() {
        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Health request received");

        return Health
                .up()
                .withDetail("device manager is happy", "smiley-face")
                .build();
    }

    // endregion

}
