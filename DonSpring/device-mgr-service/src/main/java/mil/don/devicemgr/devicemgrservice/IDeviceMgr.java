package mil.don.devicemgr.devicemgrservice;

import mil.don.common.devices.DeviceEntity;
import mil.don.common.devices.DeviceType;

public interface IDeviceMgr {

    boolean addDevice(DeviceEntity device);

    DeviceEntity getById(String id);

    DeviceEntity[] getByType(DeviceType type);

    DeviceEntity[] getAll();
}
