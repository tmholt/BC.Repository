package mil.don.devices.platform_duke5;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.conversions.ByteToTcut21EwDecoder;
import mil.don.common.coordinates.CompositeCoordinate;
import mil.don.common.coordinates.EcefCoordinate;
import mil.don.common.devices.DetectionMessage;
import mil.don.common.devices.DeviceBase;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.devices.IDevice;
import mil.don.common.devices.IDeviceCamera;
import mil.don.common.devices.IDeviceDetector;
import mil.don.common.devices.IDeviceWeapon;
import mil.don.common.logging.LoggingLevel;
import mil.don.common.messages.tcut21.BitResultStatusE;
import mil.don.common.messages.tcut21.EWMessage;
import mil.don.common.messages.tcut21.EwMode;
import mil.don.common.messages.tcut21.XYZPos;
import mil.don.common.networking.UdpTransportReceiver;
import mil.don.common.services.ILoggingService;
import mil.don.common.status.DeviceStatusMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * What are the things that a device needs to do, and what does it need in order to do that job?
 * 1. open communications with the actual device.
 *    custom ports, usually UDP
 *    custom inbound / outbound protocol
 *    turn on receiving as needed
 *    receive configuration from device or configure device as needed
 **  NEED: URIs
 * 2. convert the specific device messages into a common format and pass along
 *    need details for where to send messages
 *    need to connect to inbound commands (specifically targeted to this device)
 * * NEED: details for transport (rabbitmq, exchanges, whatever)
 * 3. connect to video stream as do whatever with it
 ** NEED: URIs
 * 4. send heartbeat / status messages out
 * 5. let device manager know that it is available as a device in the system
 * 6. set up destructors to close comms on shutdown
 */
@Component("Duke5Device")
@Scope("prototype")
public class Duke5
    extends DeviceBase
    implements IDeviceCamera, IDeviceWeapon, IDeviceDetector
{

    // outbound stream of detection events
    private final Subject<DetectionMessage> _rxDetections;


    // this is the UDP connection to a duke device for receiving detections and status
    private UdpTransportReceiver _responses;
    private DukeCommandTransport _commands;

    // we just keep up with one device status message, update this message as status comes
    // in from the duke, and then send out status on a regular 1hz heartbeat.
    private DeviceStatusMessage _status;

    // counter to keep up with number of status messages sent
    private long _messageId = 1;
    private boolean _initialized = false;
    private ScheduledExecutorService _statusPublisher;


    public Duke5(ILoggingService logging) {
        super(logging);

        _rxDetections = PublishSubject.create();

        List<DeviceCapability> caps = this.getCapabilities();
        caps.add(DeviceCapability.CAMERA);
        caps.add(DeviceCapability.WEAPON);
    }

    // outbound stream of detection events
    public Observable<DetectionMessage> getDetectionsStream() {
        return _rxDetections;
    }


    // part of being a device - the device type
    @Override
    public String getDeviceType() { return "DukeV5"; }

    // set up this device based on the inbound configuration
    @Override
    public boolean configure(DeviceConfiguration deviceConfig) {
        boolean baseOk = super.configure(deviceConfig);
        // custom reading of config properties for this specific device
        return baseOk;
    }


    // called at initialization. start anything for this device that needs
    // to be started. NOTE that the caller is NOT wrapping this in a thread,
    // as maybe this device doesn't need any. So it is up to the individual
    // device to decide what threading is needed.
    // should be @Overridden by actual device
    @Override
    public boolean start() {

        // set up our outbound status message
        initializeDeviceStatus();

        // open response port and start listening for inbound data
        listenForResponseData();

        // set up our scheduled publish of status
        // (not using @scheduled so we can create device objects)
        Runnable task = () -> sendDeviceStatus();
        _statusPublisher = Executors.newScheduledThreadPool(1);
        _statusPublisher.scheduleWithFixedDelay(task, 1, 1, TimeUnit.SECONDS);

        return true;
    }

    // stop operation - called on termination of device manager service.
    // close any threads and connections to actual device.
    // should be @Overridden by actual device implementation
    @Override
    public boolean stop()
    {
        if ( _statusPublisher != null ) {
            _statusPublisher.shutdown();
            _statusPublisher = null;
        }

        closeResponseConnection();
        return true;
    }

    // sends a device status event back to the device manager every second
    private void sendDeviceStatus()
    {
        if ( !_initialized ) return;

        // check for timeout from device
        if ( _status.getIsConnected() )
        {
            long diff = (new Date()).getTime() - _status.getTimestamp().getTime();
            if ( diff > DEVICE_TIMEOUT_PERIOD )
            {
                _status.setIsConnected(false);
                _status.setIsConnectedChanged(true);
            }
        }


        _rxStatus.onNext(_status);

        // clear changed states
        _status.setIsConnectedChanged(false);
        _status.setIsOperationalChanged(false);
        _status.setIsChanged(false);
    }


    // ability to send a command to a particular device
    // should be overridden for a specific device type
    @Override
    public boolean executeDeviceCommand(DeviceCommandBase command) {
        return true;
    }

    private void initializeDeviceStatus() {
        _status = new DeviceStatusMessage(_name, _name, getDeviceType(), new Date(), false, false);
        _initialized = true;
    }

    // close our connection to the device that is receiving detections
    private void closeResponseConnection() {
    }

    // start listening for response data on the defined port for duke outbound responses
    private void listenForResponseData() {

        // null check
        String suri = _deviceConfig.getComms().getDataUri();
        URI uri = URI.create(suri);
        // TODO: null check
        _responses = new UdpTransportReceiver(uri.getPort());
        _responses.getResponsesStream().subscribe((byte[] data) -> handleResponseData(data));
        _responses.start();
    }


    // this is the method that gets called any time we receive response data from the device
    // that we are connected to. In this case, it will either be a status message or a ew track
    // message.
    private void handleResponseData(byte[] data) {
        if ( data == null || data.length ==  0 ) return;

        if ( loggingLevelIs(LoggingLevel.DEBUG) )
        {
            String result = new String(data, 0, data.length);
            System.out.println(result);
        }

        // this is a tcut 2.1 ew message with either status or detection within
/*
    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <EWMessage revision="2.1" source_system="DukeV5">
        <DataReport time="1542139077" time_is_valid="true" msg_count="599" receipt_required="false">
            <SystemStatus sw_version="1.2.3" system_state="run" overall_status="Normal" gps_is_locked="true">
                <XYZPos x="303083.0" y="-5241679.0" z="3609125.0"/>
            </SystemStatus>
            <EWTrack track_id="1" signal_type="Default Signal Type" update_time="1542139077000000"
                affiliation="Hostile" affiliation_conf="1.0" approaching="true" azimuth="30.0" azimuth_err="1.0"
                bandwidth="1.0" elevation="30.0" elevation_err="1.0" end_track="false" frequency="1.0" group_id="1"
                mac_address="12:34:56:78:91:23" noise_strength="1.0" priority="1" quality="1" signal_conf="1.0"/>
        </DataReport>
    </EWMessage>

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <EWMessage revision="2.1" source_system="DukeV51">
        <DataReport time="1542690785" time_is_valid="true" msg_count="2850" receipt_required="false">
            <EWTrack track_id="1" signal_type="Default Signal Type" update_time="1542690785000000"
                affiliation="Hostile" affiliation_conf="1.0" approaching="true" azimuth="30.0"
                azimuth_err="1.0" bandwidth="1.0" elevation="30.0" elevation_err="1.0" end_track="false"
                frequency="1.0" group_id="1" mac_address="12:34:56:78:91:23" noise_strength="1.0"
                priority="1" quality="1" signal_conf="1.0"/>
        </DataReport>
    </EWMessage>

 */
        try
        {
            ByteToTcut21EwDecoder decoder = new ByteToTcut21EwDecoder();
            EWMessage ew = decoder.decode(data);

            // could filter out messages that got to this device that are not from the actual named device
            //String deviceName = ew.getSourceSystem();
            //if ( deviceName != this._name ) return ...

            // this message has a status and/or a detection inside

            DeviceStatusMessage status = buildDeviceStatus(ew);
            if ( status != null )
            {
                // set the values in our 'golden' status message from this one we
                // just generated. returns if any of the essential values have changed
                // (which does not include timestamp)
                setGoldenStatus(status);
            }

            DetectionMessage detection = buildDeviceDetection(ew);
            if ( detection != null ) {
                _rxDetections.onNext(detection);
            }

        }
        catch ( Exception ex ) {

        }

    }


    private DetectionMessage buildDeviceDetection(EWMessage ew) {
        DetectionMessage msg = new DetectionMessage()
            .setDetectionType("RADAR")
            .setId(Long.toString(_messageId++))
            .setSourceName(_name)
            .setSourceDeviceType(getDeviceType())
            .setTimestamp(new Date());

        return msg;
    }

    private DeviceStatusMessage buildDeviceStatus(EWMessage message)
    {
        List<EWMessage.DataReport> reports = message.getDataReport();
        if ( reports == null || reports.isEmpty() ) {
            //_logging.log("ERROR IN EWMessage::Report");
            return null;
        }

        // note the current code loops, but just ooverwrites data if there are multiple.
        // ditto for status or detections
        EWMessage.DataReport dataReport = reports.get(0);

        List<EWMessage.DataReport.SystemStatus> statuses = dataReport.getSystemStatus();
        if ( statuses == null || statuses.isEmpty() ) {
            // not a status message
            return null;
        }

        DeviceStatusMessage msg = new DeviceStatusMessage();


        // just taking the first one (again)
        EWMessage.DataReport.SystemStatus systemStatus = statuses.get(0);


        // Get the source system of the EW Message
        String sourceSystem = message.getSourceSystem();
        String isJamming = systemStatus.getSystemState() == EwMode.RUN ? "true" : "false";
        msg.getProperties().put("JammingEnabled", isJamming);

        msg.setTimestamp(new Date()); // TODO: timestamp from tcut message
        msg.setId(getId());
        msg.setIndex(_messageId++);
        msg.setSourceName(sourceSystem);
        msg.setIsConnected(true);
        msg.setIsOperational(systemStatus.getOverallStatus() == BitResultStatusE.NORMAL);

        // position information
        XYZPos xyzPos = systemStatus.getXYZPos();
        if ( xyzPos != null )
        {
            CompositeCoordinate position = msg.getPosition();
            EcefCoordinate ecef = position.setEcef(xyzPos.getX(), xyzPos.getY(), xyzPos.getZ());
            position.setLlaHaeFromEcef(ecef); // conversion
        }

        return msg;
    }

    // push the values from this inbound status into our golden one - the one
    // that is sent on a timer.
    // see if any of the key fields within our device status have changed.
    // this does not include timestamp.
    //
    private boolean setGoldenStatus(DeviceStatusMessage status) {

        boolean changed = false;

        if ( _status.getIsConnected() != status.getIsConnected() ) {
            _status.setIsConnected(status.getIsConnected());
            _status.setIsConnectedChanged(true);
            changed = true;
        }
        // else - changed values cleared after send


        if ( _status.getIsOperational() != status.getIsOperational() ) {
            _status.setIsOperational(status.getIsOperational());
            _status.setIsOperationalChanged(true);
            changed = true;
        }
        // else - changed values cleared after send


        // TODO: check jamming custom property

        // TODO: check position

        // set to latest timestamp
        _status.setTimestamp(status.getTimestamp());

        // overall change status
        _status.setIsChanged(changed);

        return changed;
    }

    // cloneable (kinda)
    @Override
    public IDevice copy() {
        Duke5 d = new Duke5(_logging);
        // d._name = this._name; //?
        return d;
    }


}
