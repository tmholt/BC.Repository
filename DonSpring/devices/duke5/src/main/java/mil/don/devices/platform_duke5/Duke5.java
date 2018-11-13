package mil.don.devices.platform_duke5;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.devices.DetectionMessage;
import mil.don.common.devices.DeviceBase;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.interfaces.IDevice;
import mil.don.common.interfaces.IDeviceCamera;
import mil.don.common.interfaces.IDeviceWeapon;
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
@Component("Duke5Device")
@Scope("prototype")
public class Duke5
    extends DeviceBase
    implements IDeviceCamera, IDeviceWeapon
{

    private final Subject<DetectionMessage> _rxDetections;
    private final Subject<DeviceStatusMessage> _rxStatus;

    private long _id = 1;



    public Duke5() {
        _rxDetections = PublishSubject.create();
        _rxStatus = PublishSubject.create();

        List<DeviceCapability> caps = this.getCapabilities();
        caps.add(DeviceCapability.CAMERA);
        caps.add(DeviceCapability.WEAPON);
    }

    public Observable<DetectionMessage> getDetectionsStream() {
        return _rxDetections;
    }

    public Observable<DeviceStatusMessage> getStatusStream() {
        return _rxStatus;
    }



    @Override
    public String getDeviceType() { return "DukeV5"; }

    @Override
    public boolean configure(DeviceConfiguration deviceConfig) {

        boolean baseOk = super.configure(deviceConfig);
        return baseOk;
    }


    @Override
    public void run() {

        int i = 0;
        while ( true ) {
            try
            {
                Thread.sleep(1000);
            }
            catch ( InterruptedException e )
            {
                break;
            }


            i++;
            _rxStatus.onNext(buildDeviceStatus());

            if ( i % 2 == 0 ) {
                _rxDetections.onNext(buildDeviceDetection());
            }
        }

    }

    private DeviceStatusMessage buildDeviceStatus() {
        return new DeviceStatusMessage(_id++, _name, getDeviceType(), new Date(), true);
    }

    private DetectionMessage buildDeviceDetection() {
        DetectionMessage msg = new DetectionMessage()
            .setDetectionType("RADAR")
            .setId(Long.toString(_id++))
            .setSourceName(_name)
            .setSourceDeviceType(getDeviceType())
            .setTimestamp(new Date());

        return msg;
    }

    // cloneable (kinda)
    public IDevice copy() {
        Duke5 d = new Duke5();
        // d._name = this._name; //?
        return d;
    }


}
