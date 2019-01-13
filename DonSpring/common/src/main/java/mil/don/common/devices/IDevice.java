package mil.don.common.devices;

import java.util.List;

import io.reactivex.Observable;
import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.messages.tcut30.StatusMessage;
import mil.don.common.status.DeviceStatusMessage;

// base functionality for a device interface within DON.
// all initialization and processing should occur from within the
// run() method; each device processes on its own thread.
public interface IDevice  {

    // region property access

    String getId();
    String getName();
    String getDeviceType();
    String getSymbolCode();
    double getRange();
    List<DeviceCapability> getCapabilities();

    // endregion

    // region events


    Observable<StatusMessage> getStatusStream();

    // endregion

    // region public methods

    // configure passes the specific configuration for a particular device
    // into that device. all necessary info needed for run should be loaded
    // at this time. A return value of false means this device is not ready
    // to connect and run.
    boolean configure(DeviceConfiguration deviceConfig);

    // ability to send a command to a particular device
    boolean executeDeviceCommand(DeviceCommandBase command);

        // connect to the associated physical device, and start processing commands
    // and messages for this device. NOTE that this call is made syncronously,
    // and a return value is expected. If a device needs to set up a processing
    // loop then it should be spun off to a separate thread.
    boolean start();

    // disconnect and stop processing messages for this device
    boolean stop();



    // returns a copy of this device information
    IDevice copy();

    // endregion

}
