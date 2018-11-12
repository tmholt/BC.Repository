package mil.don.devices.platform_nighthawk;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.devices.DetectionMessage;
import mil.don.common.devices.DeviceBase;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.interfaces.IDeviceCamera;
import mil.don.common.status.DeviceStatusMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.List;

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

    private final Subject<DeviceStatusMessage> _rxStatus = PublishSubject.create();
    private long _id = 100;



    public Nighthawk() {
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

    public Observable<DetectionMessage> getDetectionsStream() {
        return null;
    }

    public Observable<DeviceStatusMessage> getStatusStream() {
        return _rxStatus;
    }


    @Override
    public void run() {

        int i = 0;
        while ( true ) {
            try
            {
                Thread.sleep(1500);
            }
            catch ( InterruptedException e )
            {
            }


            i++;
            _rxStatus.onNext(buildDeviceStatus());

        }

    }

    private DeviceStatusMessage buildDeviceStatus() {
        return new DeviceStatusMessage(_id++, "Nighthawk", new Date(), true, this);
    }




}
