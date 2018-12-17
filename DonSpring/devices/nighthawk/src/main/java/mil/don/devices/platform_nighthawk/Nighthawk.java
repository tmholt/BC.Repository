package mil.don.devices.platform_nighthawk;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.devices.DeviceBase;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.devices.IDevice;
import mil.don.common.devices.IDeviceCamera;
import mil.don.common.services.ILoggingService;
import mil.don.common.status.DeviceStatusMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


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
@Component("NighthawkDevice")
@Scope("prototype")
public class Nighthawk
    extends DeviceBase
    implements IDeviceCamera
{

    private long _id = 100;

    private boolean _initialized = false;
    private ScheduledExecutorService _statusPublisher;

    public Nighthawk(ILoggingService logging) {
        super(logging);
        List<DeviceCapability> caps = this.getCapabilities();
        caps.add(DeviceCapability.CAMERA);
    }

    @Override
    public String getDeviceType() { return "Nighthawk"; }

    @Override
    public boolean configure(DeviceConfiguration deviceConfig) {
        boolean baseOk = super.configure(deviceConfig);
        return baseOk;
    }

    @Override
    public boolean start() {

        // set up our scheduled publish of status
        // (not using @scheduled so we can create device objects)
        Runnable task = () -> sendDeviceStatus();
        _statusPublisher = Executors.newScheduledThreadPool(1);
        _statusPublisher.scheduleWithFixedDelay(task, 1, 1, TimeUnit.SECONDS);

        return true;
    }

    @Override
    public boolean stop()
    {
        if ( _statusPublisher != null ) {
            _statusPublisher.shutdown();
            _statusPublisher = null;
        }
        return true;
    }

    // ability to send a command to a particular device
    @Override
    public boolean executeDeviceCommand(DeviceCommandBase command) {
        return true;
    }

    private void sendDeviceStatus() {
        _rxStatus.onNext(buildDeviceStatus());
    }

    private DeviceStatusMessage buildDeviceStatus() {
        DeviceStatusMessage msg = new DeviceStatusMessage();
        msg.setTimestamp(new Date());
        msg.setId(getId());
        msg.setIndex(_id++);
        msg.setSourceName(getName());
        msg.setIsConnected(true);
        msg.setIsOperational(true);
        return msg;
    }

    // cloneable (kinda)
    @Override
    public IDevice copy() {
        Nighthawk d = new Nighthawk(_logging);
        // d._name = this._name; //?
        return d;
    }




}
