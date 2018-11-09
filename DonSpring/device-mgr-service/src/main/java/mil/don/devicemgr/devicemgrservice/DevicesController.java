package mil.don.devicemgr.devicemgrservice;


import mil.don.common.devices.DeviceEntity;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.interfaces.IDevice;
import mil.don.devicemgr.devicemgrservice.configuration.AppConfig;
import mil.don.devicemgr.devicemgrservice.configuration.GlobalConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DevicesController {

    @Autowired
    private Environment _env;

    @Autowired
    private IDeviceMgr _repo;

    // this is the configuration for this device manager service
    @Autowired
    private AppConfig _appConfig;

    // this is the config everyone gets
    @Autowired
    private GlobalConfig _globalConfig;


    // test for remote configuration server
    @GetMapping("/devices/configuration")
    public String showConfiguration() {
        return _appConfig.toString();
    }

    @GetMapping("/devices/byid/{id}")
    public IDevice getById(@PathVariable String id) {
        return _repo.getById(id);
    }

    @GetMapping("/devices/bycap/{cap}")
    public IDevice[] getByCapability(@PathVariable DeviceCapability cap) {
        return (_repo.getByCapability(cap));
    }

    @GetMapping("/devices")
    public IDevice[] getAll() {
        return _repo.getAll();
    }

    public int getServicePort() {
        return Integer.parseInt(_env.getProperty("local.server.port"));
    }



}
