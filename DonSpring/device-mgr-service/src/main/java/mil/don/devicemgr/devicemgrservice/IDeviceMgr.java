package mil.don.devicemgr.devicemgrservice;

import java.util.List;

import mil.don.common.devices.DeviceBase;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.devices.IDevice;


public interface IDeviceMgr {

    boolean addDevice(DeviceBase device);

    DeviceBase getDeviceById(String id);

    List<DeviceBase> getDevicesByCapability(DeviceCapability type);

    List<DeviceBase> getAllDevices();

    boolean executeDeviceCommand(DeviceCommandBase command);
}
