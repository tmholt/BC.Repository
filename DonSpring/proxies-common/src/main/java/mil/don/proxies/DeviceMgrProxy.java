package mil.don.proxies;


import feign.Headers;
import mil.don.common.devices.DeviceBase;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.services.IDeviceManagerService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="don.service.device-mgr",
    url="http://localhost:8011") // port lookup (vs eureka)

public interface DeviceMgrProxy extends IDeviceManagerService
{
    @GetMapping("/devices/configuration")
    String getConfiguration();

    @RequestMapping(value="/devices/byid/{id}")
    DeviceBase getDeviceById(
            @RequestParam("id")String id);

    @RequestMapping(value="/devices/bycap/{cap}")
    DeviceBase[] getDevicesByCapability(
            @RequestParam("cap") DeviceCapability cap);

    @RequestMapping(value="/devices")
    DeviceBase[] getAllDevices();

    @Headers("Content-Type: application/json")
    @RequestMapping(method = RequestMethod.POST, value = "/device/command")
    String executeCommand(@RequestBody DeviceCommandBase command);


}

