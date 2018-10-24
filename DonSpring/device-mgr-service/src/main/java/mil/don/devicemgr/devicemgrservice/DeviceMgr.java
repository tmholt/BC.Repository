package mil.don.devicemgr.devicemgrservice;


import mil.don.common.devices.DeviceEntity;
import mil.don.common.devices.DeviceType;
import mil.don.common.logging.Priority;
import mil.don.proxies.LoggingProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class DeviceMgr
        extends HashMap<String, DeviceEntity>
        implements HealthIndicator, IDeviceMgr {


    @Autowired
    private LoggingProxy _logging;


    public DeviceMgr() {
    }

    public boolean addDevice(DeviceEntity device) {
        if ( this.containsKey(device.getId()) ) {
            return false;
        }
        this.put(device.getId(), device);

        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device added to configuration");
        return true;
    }

    public DeviceEntity getById(String id) {

        if ( this.containsKey(id) ) {
            _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device lookup success: " + id);
            return this.get(id);
        }
        else {
            _logging.log(Priority.WARNING, "DeviceMgrService::DeviceMgr", "Request for bad device id");
            return null;
        }
    }

    public DeviceEntity[] getByType(DeviceType type) {
        ArrayList<DeviceEntity> list = new ArrayList<>();
        for ( DeviceEntity entity : this.values() ) {
            List<DeviceType> capabilities = entity.getCapabilities();
            if ( capabilities.contains(type) ) list.add(entity);
        }

        int count = list.size();
        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device type " + type + " lookup found: " + count);
        return list.toArray(new DeviceEntity[count]);
    }

    public DeviceEntity[] getAll() {
        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Device list request: " + this.size());
        return this.values().toArray(new DeviceEntity[this.size()]);
    }

    // region HealthIndicator implementation

    @Override
    public Health health() {
        _logging.log(Priority.DEBUG, "DeviceMgrService::DeviceMgr", "Health request received");

        return Health
                .up()
                .withDetail("device manager is happy", "smiley-face")
                .build();
    }

    // endregion

}
