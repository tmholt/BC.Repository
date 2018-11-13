package mil.don.common.interfaces;

import java.util.List;

import io.reactivex.Observable;
import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.configuration.SystemCommsConfiguration;
import mil.don.common.devices.DetectionMessage;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.status.DeviceStatusMessage;

// base functionality for a device interface within DON.
// all initialization and processing should occur from within the
// run() method; each device processes on its own thread.
public interface IDevice extends Runnable {

    // region property access

    String getId();
    String getName();
    String getDeviceType();
    String getSymbolCode();
    double getRange();
    List<DeviceCapability> getCapabilities();

    // endregion

    // region events

    Observable<DetectionMessage> getDetectionsStream();
    Observable<DeviceStatusMessage> getStatusStream();

    // endregion

    // region public methods

    boolean configure(DeviceConfiguration deviceConfig);
    IDevice copy();

    // endregion

}
