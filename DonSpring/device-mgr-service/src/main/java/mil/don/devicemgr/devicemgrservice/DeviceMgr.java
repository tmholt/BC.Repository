package mil.don.devicemgr.devicemgrservice;


import io.reactivex.Observable;
import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.devices.DetectionMessage;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.devices.IDevice;
import mil.don.common.devices.IDeviceDetector;
import mil.don.common.logging.LoggingEntry;
import mil.don.common.logging.LoggingLevel;
import mil.don.common.status.DeviceStatusMessage;
import mil.don.common.status.ServiceStatusMessage;
import mil.don.devicemgr.devicemgrservice.configuration.AppConfig;
import mil.don.devicemgr.devicemgrservice.configuration.GlobalConfig;
import mil.don.proxies.LoggingProxy;


import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;


@Service
public class DeviceMgr
        extends HashMap<String, IDevice>
        implements IDeviceMgr { // HealthIndicator


    // this is the topic name that service status events will be pushed to
    public static final String SERVICE_STATUS_ROUTING_KEY = "status.service";
    public static final String DEVICE_STATUS_ROUTING_KEY = "status.device";
    public static final String DEVICE_DETECTION_ROUTING_KEY = "device.detection";
    public static final String DEVICE_COMMAND_ROUTING_KEY = "device.command";


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

    // this is the rabbitmq exchange that will contain all sent status events, both from services and
    // from configured devices.
    private Exchange _statusExchange;

    // this is the rabbitmq exchange that will contain all detection events from devices that perform detections
    private Exchange _detectionsExchange;




    @PostConstruct
    public void initialize() {
        log(LoggingLevel.TRACE, "DeviceMgr initialization");
        initializeExchanges();
        loadDevicesFromConfiguration();
        log(LoggingLevel.TRACE, "DeviceMgr initialization complete");
   }


   /**
    * this was using a rabbitmq channel for receiving commands. changed to using a
    * direct api command
    @RabbitListener(queues="device-commands-queue")
    public void receiveDeviceCommands(final DeviceCommandBase command) {
        System.out.println("received device command: " + command.toString());
    }
    **/

    // sends a service status event every 5 seconds over rabbitmq
    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    void exchangeServiceStatusEvent() {
        ServiceStatusMessage status = new ServiceStatusMessage(getServiceName(), getServiceName(), new Date(), true);
        _rabbitTemplate.convertAndSend(_statusExchange.getName(), SERVICE_STATUS_ROUTING_KEY, status );
        System.out.println("sent service status event: " + status.toString());
    }

    void exchangeDeviceStatusEvent(DeviceStatusMessage status) {
        _rabbitTemplate.convertAndSend(_statusExchange.getName(), DEVICE_STATUS_ROUTING_KEY, status);
        System.out.println("sent device status event: " + status.toString());
    }

    void exchangeDeviceDetectionEvent(DetectionMessage detection) {
        _rabbitTemplate.convertAndSend(_detectionsExchange.getName(), DEVICE_DETECTION_ROUTING_KEY, detection);
        System.out.println("sent device detection event: " + detection.toString());
    }

    private void initializeExchanges() {
        _statusExchange = new FanoutExchange("status-events", true, false);
        _detectionsExchange = new FanoutExchange("detection-events", true, false);

    }

    // go through our config file and create the correct IDevice
    // for it.
    private void loadDevicesFromConfiguration() {
        for ( DeviceConfiguration deviceConfig : _appConfig.getDevices() ) {

            String lookup = deviceConfig.getType();
            IDevice handler = findDeviceByType(lookup);
            if ( handler == null ) {
                log(LoggingLevel.ERROR, "ERROR finding device type " + lookup);
                continue;
            }

            // create a specific copy of our template device and crank it up
            IDevice specific = createSpecificDevice(handler, deviceConfig);
            if ( specific == null ) {
                log(LoggingLevel.ERROR, "ERROR creating device type " + lookup);
                continue;
            }

            log(LoggingLevel.INFO, "created device: " + specific.getName());
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

    // create our new device, hook to events from it, and start its
    // run method in a new thread.
    private IDevice createSpecificDevice(IDevice source,
         DeviceConfiguration config) {

        IDevice specific = source.copy();
        specific.configure(config);
        wireupDeviceEvents(specific);

        boolean started = specific.start();
        return started ? specific : null;
    }

    private void wireupDeviceEvents(IDevice device) {

        // connect to status events from this device
        Observable<DeviceStatusMessage> statuses = device.getStatusStream();
        if ( statuses != null )
        {
            statuses.subscribe(
                // on next
                (DeviceStatusMessage status) -> {
                    exchangeDeviceStatusEvent(status);
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
        if ( device instanceof IDeviceDetector )
        {
            Observable<DetectionMessage> detects = ((IDeviceDetector)device).getDetectionsStream();
            if ( detects != null )
            {
                detects.subscribe(
                    // on next
                    (DetectionMessage detection) -> {
                        exchangeDeviceDetectionEvent(detection);
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
    }

    private String log(LoggingLevel p, String message) {
      LoggingEntry log = new LoggingEntry(new Date(), p, "DeviceManagerService::DeviceMgr", message);
      return _logging.log(log);
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

        _logging.debug("DeviceMgrService::DeviceMgr", "Device added to configuration");
        return true;
    }

    public IDevice getDeviceById(String id) {

        if ( this.containsKey(id) ) {
            _logging.debug("DeviceMgrService::DeviceMgr", "Device lookup success: " + id);
            return this.get(id);
        }
        else {
            _logging.warn("DeviceMgrService::DeviceMgr", "Request for bad device id");
            return null;
        }
    }

    public List<IDevice> getDevicesByCapability(DeviceCapability cap) {

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

        return list1;
    }

    public List<IDevice> getAllDevices() {
        _logging.debug("DeviceMgrService::DeviceMgr", "Device list request: " + this.size());
        return new ArrayList<>(this.values());
    }

    public boolean executeDeviceCommand(DeviceCommandBase command) {

        if ( command == null ) return false;

        String id = command.getDeviceId();
        if ( id == null || id.length() == 0 ) return false;

        // TODO: validate client token



        if ( this.containsKey(id) ) {
            _logging.debug("DeviceMgrService::executeDeviceCommand", "Device lookup success: " + id);
            IDevice device = this.get(id);
            return device.executeDeviceCommand(command);
        }
        else {
            _logging.warn("DeviceMgrService::executeDeviceCommand", "Request for bad device id");
            return false;
        }

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

}
