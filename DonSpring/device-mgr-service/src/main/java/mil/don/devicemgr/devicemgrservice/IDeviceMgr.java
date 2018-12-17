package mil.don.devicemgr.devicemgrservice;

import java.util.List;

import mil.don.common.devices.DeviceCapability;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.devices.IDevice;


public interface IDeviceMgr {

    boolean addDevice(IDevice device);

    IDevice getDeviceById(String id);

    List<IDevice> getDevicesByCapability(DeviceCapability type);

    List<IDevice> getAllDevices();

    boolean executeDeviceCommand(DeviceCommandBase command);
}
