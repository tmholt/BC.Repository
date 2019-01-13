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
import mil.don.common.messages.tcut30.BitResultStatusE;
import mil.don.common.messages.tcut30.EwMode;
import mil.don.common.messages.tcut30.StatusMessage;
import mil.don.common.messages.tcut30.SystemTypeE;
import mil.don.common.services.ILoggingService;
import mil.don.common.status.DeviceStatusMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static mil.don.common.conversions.DeviceStatusToTcut30StatusConverter.SYSTEM_MISSION_NAME;
import static mil.don.common.conversions.DeviceStatusToTcut30StatusConverter.SYSTEM_SW_VERSION;

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

    private StatusMessage buildDeviceStatus() {

      StatusMessage result = new StatusMessage();
      result.setRevision("3.0");
      result.setSourceSystem(this.getName());
      result.setSourceType(SystemTypeE.CAMERA);

      result.setTime(BigInteger.valueOf(System.currentTimeMillis()));
      result.setTimeIsValid(false);

      // message count. expected to be 0 for status
      result.setMsgCount(0);

      // set up the internal StatusMessage subclass
      StatusMessage.Status internalStatus = new StatusMessage.Status();
      internalStatus.setSwVersion(SYSTEM_SW_VERSION);
      internalStatus.setOverallStatus(BitResultStatusE.NORMAL);
      internalStatus.setMission(SYSTEM_MISSION_NAME); // NOTE: REQUIRED
      internalStatus.setSystemState(EwMode.HALT); // taken from 21 conversion. why HALT??
      // no spacial factors
      // no optical factors
      // no mission
      // no coordination factors
      // no rf factors
      // no hardware version
      result.setStatus(internalStatus);
      return result;
    }

    // cloneable (kinda)
    @Override
    public DeviceBase copy() {
        Nighthawk d = new Nighthawk(_logging);
        copyBase(d);
        // copy anything specific to nighthawk
        return d;
    }




}
