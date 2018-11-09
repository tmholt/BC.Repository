package mil.don.devicemgr.devicemgrservice;


import mil.don.common.configuration.Device;
import mil.don.common.devices.DeviceEntity;
import mil.don.common.devices.DeviceCapability;
import mil.don.common.interfaces.IDevice;
import mil.don.devicemgr.devicemgrservice.configuration.AppConfig;
import mil.don.devices.platform_nighthawk.Nighthawk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class DeviceProvider implements ApplicationContextAware
{

    // our core device manager
    private IDeviceMgr _mgr;

    // this is the configuration for this device manager service
    private AppConfig _appConfig;

    ApplicationContext _context;

    @Autowired
    public DeviceProvider(IDeviceMgr mgr, AppConfig appConfig) {
        _mgr = mgr;
        _appConfig = appConfig;
    }

    // implementation of ApplicationContextAware. basically gives us access
    // to the ioc container in spring
    public void setApplicationContext(ApplicationContext applicationContext) {
        _context = applicationContext;
    }

    // so... i would like to avoid this baked in knowledge. can you expose
    // meta-data about a component, and then load that component based on the
    // meta-data, without having to create them all and then ask each one if they
    // are indeed a nighthawk or whatever?
    @PostConstruct
    private void init() {

        for ( Device deviceConfig : _appConfig.getDevices() ) {
            switch ( deviceConfig.getType().toUpperCase() ) {
                case "DUKE":
                case "CROWS":
                    break;
                case "NIGHTHAWK":
                    IDevice nhawk = _context.getBean(Nighthawk.class);
                    _mgr.addDevice(nhawk);
                    break;
            }
        }

    }

    private void dummy_init() {

        DeviceEntity wisp = new DeviceEntity("wisp-id-1234", "WISP", "WISP1");
        wisp.getPosition().setLlaHae(36.7, -86.6, 20);
        wisp.getCapabilities().add(DeviceCapability.CAMERA);
        wisp.getCapabilities().add(DeviceCapability.RADAR);

        DeviceEntity crows = new DeviceEntity("crows-id-2345", "CROWS", "CROWS1");
        crows.getPosition().setLlaHae(36.71, -86.61, 20);
        crows.getCapabilities().add(DeviceCapability.WEAPON);

        DeviceEntity duke = new DeviceEntity("duke-id-3223", "DUKE", "DUKE1");
        duke.getPosition().setLlaHae(36.72, -86.62, 20);
        duke.getCapabilities().add(DeviceCapability.WEAPON);
        duke.getCapabilities().add(DeviceCapability.RADAR);

        DeviceEntity nhawk = new DeviceEntity("nighthawk-id-3323", "NIGHTHAWK", "NIGHTHAWK1");
        nhawk.getPosition().setLlaHae( 36.73, -86.63, 20);
        nhawk.getCapabilities().add(DeviceCapability.CAMERA);


        _mgr.addDevice(wisp);
        _mgr.addDevice(crows);
        _mgr.addDevice(duke);
        _mgr.addDevice(nhawk);
    }


}

