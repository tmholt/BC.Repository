package mil.don.devicemgr.devicemgrservice;


import mil.don.common.devices.DeviceBase;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.interfaces.IDevice;
import mil.don.common.services.IDeviceManagerService;
import mil.don.devicemgr.devicemgrservice.configuration.AppConfig;
import mil.don.devicemgr.devicemgrservice.configuration.GlobalConfig;
import mil.don.proxies.LoggingProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    // we have a configuration file of the same name as this service in the
    // configuration service, which gets auto-loaded and sent just to this service.
    // pretty nifty.
    @Autowired
    private AppConfig _appConfig;

    // this is the config everyone gets. it is loaded from application.yml within
    // the configuration service, and sent to any service requesting it.
    @Autowired
    private GlobalConfig _globalConfig;

    // the proxy to our logging service
    // TODO: what is the standard within spring for doing logging?
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

        _logging.debug("DevicesController::getDeviceById", "Device lookup for " + device);
        return device;
    }

    @GetMapping("/devices/bycap/{cap}")
    public DeviceBase[] getDevicesByCapability(@PathVariable DeviceCapability cap) {
        List<IDevice> list = _repo.getDevicesByCapability(cap);

        int count = list.size();
        _logging.debug("DevicesController::getDevicesByCapability", "Device cap " + cap + " lookup found: " + count);

        return list.toArray(new DeviceBase[count]);
    }

    @GetMapping("/devices")
    public DeviceBase[] getAllDevices() {
        List<IDevice> list  = _repo.getAllDevices();

        int count = list.size();
        _logging.debug("DevicesController::getAllDevices", "All devices found: " + count);

        return list.toArray(new DeviceBase[count]);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/device/command")
    public String executeCommand(@RequestBody DeviceCommandBase command) {
        boolean result = _repo.executeDeviceCommand(command);

        String log = String.format("received '%s', result='%s'", command, result);
        _logging.info("DeviceMgr::DevicesController", log);

        return String.valueOf(result);
    }

//    private int getServicePort() {
//        return Integer.parseInt(_env.getProperty("local.server.port"));
//    }



}
