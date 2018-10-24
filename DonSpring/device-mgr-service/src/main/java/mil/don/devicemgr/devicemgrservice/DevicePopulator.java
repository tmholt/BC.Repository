package mil.don.devicemgr.devicemgrservice;

import mil.don.common.devices.DeviceEntity;
import mil.don.common.devices.DeviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DevicePopulator implements HealthIndicator {

    private IDeviceMgr _mgr;

    @Autowired
    public DevicePopulator(IDeviceMgr mgr) {
        _mgr = mgr;
        init();
    }

    public void init() {

        DeviceEntity wisp = new DeviceEntity("wisp-id-1234", "WISP1", 36.7, -86.6, 20);
        wisp.getCapabilities().add(DeviceType.CAMERA);
        wisp.getCapabilities().add(DeviceType.RADAR);

        DeviceEntity crows = new DeviceEntity("crows-id-2345", "CROWS1", 36.71, -86.61, 20);
        crows.getCapabilities().add(DeviceType.WEAPON);

        DeviceEntity duke = new DeviceEntity("duke-id-3223", "DUKE1", 36.72, -86.62, 20);
        duke.getCapabilities().add(DeviceType.WEAPON);
        duke.getCapabilities().add(DeviceType.RADAR);

        DeviceEntity nhawk = new DeviceEntity("nighthawk-id-3323", "NIGHTHAWK1", 36.73, -86.63, 20);
        nhawk.getCapabilities().add(DeviceType.CAMERA);


        _mgr.addDevice(wisp);
        _mgr.addDevice(crows);
        _mgr.addDevice(duke);
        _mgr.addDevice(nhawk);
    }

    // region HealthIndicator implementation

    @Override
    public Health health() {
        return Health
                .up()
                .withDetail("device populator", "added 4 devices")
                .build();
    }

    // endregion
}

