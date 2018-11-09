package mil.don.proxies;


import mil.don.common.devices.DeviceEntity;
import mil.don.common.devices.DeviceCapability;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="don.service.device-mgr") // eureka lookup
public interface DeviceMgrProxy {

    @RequestMapping(value="/devices/byid/{id}")
    DeviceEntity getById(
            @RequestParam("id")String id);

    @RequestMapping(value="/devices/bycap/{cap}")
    DeviceEntity[] getByCapability(
            @RequestParam("cap") DeviceCapability cap);

    @RequestMapping(value="/devices")
    DeviceEntity[] getAll();


}

