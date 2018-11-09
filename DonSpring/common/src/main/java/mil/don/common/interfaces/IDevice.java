package mil.don.common.interfaces;

import java.util.List;

import mil.don.common.devices.DeviceCapability;

// base functionality for a device interface within DON.
// all initialization and processing should occur from within the
// run() method; each device processes on its own thread.
public interface IDevice extends Runnable {

    String getId();
    String getName();
    String getDeviceType();
    List<DeviceCapability> getCapabilities();

}
