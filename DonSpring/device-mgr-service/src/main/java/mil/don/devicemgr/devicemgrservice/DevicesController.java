package mil.don.devicemgr.devicemgrservice;


import mil.don.common.devices.DeviceBase;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.interfaces.IDevice;
import mil.don.common.logging.Priority;
import mil.don.common.services.IDeviceManagerService;
import mil.don.devicemgr.devicemgrservice.configuration.AppConfig;
import mil.don.devicemgr.devicemgrservice.configuration.GlobalConfig;
import mil.don.proxies.LoggingProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DevicesController implements IDeviceManagerService
{

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

    // the proxy to our logging service
    @Autowired
    private LoggingProxy _logging;


    // test for remote configuration server
    @GetMapping("/devices/configuration")
    public String getConfiguration() {
        return _appConfig.toString();
    }

    @GetMapping("/devices/byid/{id}")
    public DeviceBase getDeviceById(@PathVariable String id) {
        DeviceBase device = (DeviceBase) _repo.getDeviceById(id);

        _logging.log(Priority.DEBUG, "DevicesController::getDeviceById", "Device lookup for " + device);
        return device;
    }

    @GetMapping("/devices/bycap/{cap}")
    public DeviceBase[] getDevicesByCapability(@PathVariable DeviceCapability cap) {
        List<IDevice> list = _repo.getDevicesByCapability(cap);

        int count = list.size();
        _logging.log(Priority.DEBUG, "DevicesController::getDevicesByCapability", "Device cap " + cap + " lookup found: " + count);

        return list.toArray(new DeviceBase[count]);
    }

    @GetMapping("/devices")
    public DeviceBase[] getAllDevices() {
        List<IDevice> list  = _repo.getAllDevices();

        int count = list.size();
        _logging.log(Priority.DEBUG, "DevicesController::getAllDevices", "All devices found: " + count);

        return list.toArray(new DeviceBase[count]);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/device/command")
    public String executeCommand(DeviceCommandBase command) {
        _repo.executeDeviceCommand(command);
        return "ok";
    }
    public int getServicePort() {
        return Integer.parseInt(_env.getProperty("local.server.port"));
    }



}
