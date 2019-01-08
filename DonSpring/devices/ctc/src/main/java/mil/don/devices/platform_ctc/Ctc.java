package mil.don.devices.platform_ctc;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.conversions.ByteToCtcMessageDecoder;
import mil.don.common.conversions.CtcToTcutTrackConverter;
import mil.don.common.devices.DetectionMessage;
import mil.don.common.devices.DeviceBase;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.devices.IDevice;
import mil.don.common.devices.IDeviceCamera;
import mil.don.common.devices.IDeviceDetector;
import mil.don.common.logging.LoggingLevel;
import mil.don.common.messages.Ctc.CtcMessage;
import mil.don.common.messages.Ctc.VeDropMessage;
import mil.don.common.messages.Ctc.VeTrackUpdateMessage;
import mil.don.common.messages.tcut30.DataMessage;
import mil.don.common.messages.tcut30.SystemTypeE;
import mil.don.common.messages.tcut30.XYZPos;
import mil.don.common.messages.tcut30.XYZVel;
import mil.don.common.networking.UdpTransportReceiver;
import mil.don.common.services.ILoggingService;
import mil.don.common.status.DeviceStatusMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.math.BigInteger;
import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
@Component("CtcDevice")
@Scope("prototype")
public class Ctc
    extends DeviceBase
    implements IDeviceDetector
{
    // outbound stream of detection events
    private final Subject<DataMessage> _rxDetections;

    // this is the UDP connection to a duke device for receiving detections and status
    private UdpTransportReceiver _responses;

    // we just keep up with one device status message, update this message as status comes
    // in from the duke, and then send out status on a regular 1hz heartbeat.
    private DeviceStatusMessage _status;

    private long _index = 100;

    private boolean _initialized = false;
    private ScheduledExecutorService _statusPublisher;

    public Ctc(ILoggingService logging) {
        super(logging);

        _rxDetections = PublishSubject.create();

        List<DeviceCapability> caps = this.getCapabilities();
        caps.add(DeviceCapability.RADAR);
    }

    // outbound stream of detection events
    public Observable<DataMessage> getDetectionsStream() {
      return _rxDetections;
    }

    @Override
    public String getDeviceType() { return "Ctc"; }

    @Override
    public boolean configure(DeviceConfiguration deviceConfig) {
        boolean baseOk = super.configure(deviceConfig);
        return baseOk;
    }

    @Override
    public boolean start() {

        // set up our outbound status message
        _status = new DeviceStatusMessage(_name, _name, getDeviceType(), new Date(), false, false);

        // open response port and start listening for inbound data
        listenForResponseData();

        // set up our scheduled publish of status
        // (not using @scheduled so we can create device objects)
        Runnable task = () -> sendDeviceStatus();
        _statusPublisher = Executors.newScheduledThreadPool(1);
        _statusPublisher.scheduleWithFixedDelay(task, 1, 1, TimeUnit.SECONDS);

        _initialized = true;

        return true;
    }

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

    // ability to send a command to a particular device
    @Override
    public boolean executeDeviceCommand(DeviceCommandBase command) {
        return true;
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
    private void handleResponseData(byte[] data)
    {
        if (data == null || data.length == 0) return;

        if ( loggingLevelIs(LoggingLevel.DEBUG) )
        {
          String result = new String(data, 0, data.length);
          System.out.println(result);
        }

        // decode into CTC message and push out
        ByteToCtcMessageDecoder decoder = new ByteToCtcMessageDecoder();
        CtcMessage message = decoder.decode(data);

        CtcToTcutTrackConverter converter = new CtcToTcutTrackConverter();
        DataMessage.Track track = converter.convert(message);


        if ( track != null ) {
          Instant instant = Instant.now();
          long now = (instant.getEpochSecond() * 1000 * 1000) + (instant.getNano() / 1000);

          DataMessage dataMessage = new DataMessage();
          dataMessage.setRevision("3.0");
          dataMessage.setSourceSystem("CTC");
          dataMessage.setSourceType(SystemTypeE.RADAR);
          dataMessage.setTime(BigInteger.valueOf(now));
          dataMessage.setTimeIsValid(true);

          dataMessage.getTrack().add(track);

          _rxDetections.onNext(dataMessage);
        }

        // Update the status of the sensor (Q50) since we received a track from it
        // sensorTimeStamps.put(id.toString(), seconds);


    }

    // close our connection to the device that is receiving detections
    private void closeResponseConnection() {
    }

    private void sendDeviceStatus() {
        _rxStatus.onNext(buildDeviceStatus());
    }



    private DeviceStatusMessage buildDeviceStatus() {
        DeviceStatusMessage msg = new DeviceStatusMessage();
        msg.setTimestamp(new Date());
        msg.setId(getId());
        msg.setIndex(_index++);
        msg.setSourceName(getName());
        msg.setIsConnected(true);
        msg.setIsOperational(true);
        return msg;
    }



    // cloneable (kinda)
    @Override
    public IDevice copy() {
        Ctc d = new Ctc(_logging);
        // d._name = this._name; //?
        return d;
    }




}
