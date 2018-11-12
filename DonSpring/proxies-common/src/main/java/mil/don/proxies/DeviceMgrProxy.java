package mil.don.proxies;


import mil.don.common.devices.DeviceCapability;
import mil.don.common.interfaces.IDevice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="don.service.device-mgr") // eureka lookup
public interface DeviceMgrProxy {

    @RequestMapping(value="/devices/byid/{id}")
    IDevice getById(
            @RequestParam("id")String id);

    @RequestMapping(value="/devices/bycap/{cap}")
    IDevice[] getByCapability(
            @RequestParam("cap") DeviceCapability cap);

    @RequestMapping(value="/devices")
    IDevice[] getAll();


}

