package mil.don.devicemgr.devicemgrservice;

import mil.don.common.devices.DeviceCapability;
import mil.don.common.interfaces.IDevice;


public interface IDeviceMgr {

    boolean addDevice(IDevice device);

    IDevice getDeviceById(String id);

    IDevice[] getDevicesByCapability(DeviceCapability type);

    IDevice[] getAllDevices();
}
