package mil.don.devicemgr.devicemgrservice;


import com.netflix.appinfo.ApplicationInfoManager;

import io.reactivex.Observable;
import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.configuration.SystemCommsConfiguration;
import mil.don.common.devices.DetectionMessage;
import mil.don.common.devices.DeviceBase;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.interfaces.IDevice;
import mil.don.common.logging.Priority;
import mil.don.common.status.DeviceStatusMessage;
import mil.don.common.status.ServiceStatus;
import mil.don.devicemgr.devicemgrservice.configuration.AppConfig;
import mil.don.devicemgr.devicemgrservice.configuration.GlobalConfig;
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

    // incrementer for status events
    private static long _statusId = 1;

    // the proxy to our logging service
    @Autowired
    private LoggingProxy _logging;

    // this is the configuration for this device manager service
    @Autowired
    private AppConfig _appConfig;

    // this is the config everyone gets
    @Autowired
    private GlobalConfig _globalConfig;

    // list of all registered (discovery) IDevices
    @Autowired
    private List<IDevice> _availableDevices;

    // rabbitmq template
    @Autowired
    private RabbitTemplate _rabbitTemplate;

    // rabbitmq exchange
    @Autowired
    private Exchange _exchange;



    public DeviceMgr() {
    }


    @PostConstruct
    public void initialize() {
        loadDevicesFromConfiguration();
    }

    // go through our config file and create the correct IDevice
    // for it.
    private void loadDevicesFromConfiguration() {
        for ( DeviceConfiguration deviceConfig : _appConfig.getDevices() ) {

            String lookup = deviceConfig.getType();
            IDevice handler = findDeviceByType(lookup);
            if ( handler == null ) {
                log(Priority.ERROR, "ERROR finding device type " + lookup);
                continue;
            }

            IDevice specific = createSpecificDevice(handler, deviceConfig);
            if ( specific == null ) {
                log(Priority.ERROR, "ERROR creating device type " + lookup);
                continue;
            }

            log(Priority.INFO, "created device: " + specific.getName());
            wireupDeviceEvents(specific);
            addDevice(specific);
        }
    }

    // go through our "template" devices looking for one with the given type
    private IDevice findDeviceByType(String deviceType) {
        // I miss c# .firstOrDefault
        return _availableDevices.stream()
            .filter(dvc -> dvc.getDeviceType().equalsIgnoreCase(deviceType))
            .findFirst()
            .orElse(null);
    }

    private IDevice createSpecificDevice(IDevice source,
         DeviceConfiguration config) {

        IDevice specific = source.clone();
        specific.configure(config);
        wireupDeviceEvents(specific);

        Thread t = new Thread(specific);
        t.start();

        // TODO: think about this pattern. I like the device being the place to keep
        // the run thread, but don't like casting to a base class.
        DeviceBase deviceBase = (DeviceBase) specific;
        deviceBase.setRunThread(t);

        return specific;
    }

    private void wireupDeviceEvents(IDevice device) {

        // connect to status events from this device
        Observable<DeviceStatusMessage> statii = device.getStatusStream();
        if ( statii != null )
        {
            statii.subscribe(
                // on next
                (DeviceStatusMessage status) -> {
                    System.out.println("got device status: " + status);
                },
                // on error
                (Throwable error) -> {
                    System.out.println("ERROR in status stream: " + error);
                },
                // on completion
                () -> {
                    System.out.println("STREAM COMPLETE");
                });
        }

        // connect to detection events from this device
        Observable<DetectionMessage> detects = device.getDetectionsStream();
        if ( detects != null )
        {
            detects.subscribe(
                // on next
                (DetectionMessage detection) -> {
                    System.out.println("got detection message: " + detection);
                },
                // on error
                (Throwable error) -> {
                    System.out.println("ERROR in detections stream: " + error);
                },
                // on completion
                () -> {
                    System.out.println("STREAM COMPLETE");
                });
        }

    }

    private void log(Priority p, String message) {
        try {
            _logging.log(p, "DeviceManagerService::DeviceMgr", message);
        }
        catch (Exception ex) {
        }
    }



    // sends a status event every 5 seconds over rabbitmq
    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    void sendServiceStatusEvent() {
        ServiceStatus status = new ServiceStatus(_statusId++, getServiceName(), new Date(), true);
        _rabbitTemplate.convertAndSend(_exchange.getName(), ROUTING_KEY, status );
        System.out.println("sent service status event: " + status.toString());
    }

    // TODO: should be part of IDonService or something like that
    public String getServiceName() {
        return "DeviceManagerService";
    }


    public boolean addDevice(IDevice device) {
        if ( this.containsKey(device.getId()) ) {
            return false;
        }
        this.put(device.getId(), device);

        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device added to configuration");
        return true;
    }

    public IDevice getDeviceById(String id) {

        if ( this.containsKey(id) ) {
            _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device lookup success: " + id);
            return this.get(id);
        }
        else {
            _logging.log(Priority.WARNING, "DeviceMgrService::DeviceMgr", "Request for bad device id");
            return null;
        }
    }

    public IDevice[] getDevicesByCapability(DeviceCapability cap) {

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

    public IDevice[] getAllDevices() {
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

}
