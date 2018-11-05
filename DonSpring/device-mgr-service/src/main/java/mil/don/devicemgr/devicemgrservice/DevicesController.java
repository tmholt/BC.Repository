package mil.don.devicemgr.devicemgrservice;


import mil.don.common.devices.DeviceEntity;
import mil.don.common.devices.DeviceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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


    @GetMapping("/devices/byid/{id}")
    public DeviceEntity getById(@PathVariable String id) {
        return _repo.getById(id);
    }

    @GetMapping("/devices/bytype/{type}")
    public DeviceEntity[] getByType(@PathVariable DeviceType type) {
        return (_repo.getByType(type));
    }

    @GetMapping("/devices")
    public DeviceEntity[] getAll() {
        return _repo.getAll();
    }

    public int getServicePort() {
        return Integer.parseInt(_env.getProperty("local.server.port"));
    }



}
