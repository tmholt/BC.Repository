package mil.don.devicemgr.devicemgrservice;

import mil.don.common.devices.DeviceCapability;
import mil.don.common.interfaces.IDevice;


public interface IDeviceMgr {

    boolean addDevice(IDevice device);

    IDevice getById(String id);

    IDevice[] getByCapability(DeviceCapability type);

    IDevice[] getAll();
}
