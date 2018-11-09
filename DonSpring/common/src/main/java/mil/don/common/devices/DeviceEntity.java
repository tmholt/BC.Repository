package mil.don.common.devices;

import java.util.ArrayList;
import java.util.List;

import mil.don.common.coordinates.CompositeCoordinate;
import mil.don.common.interfaces.IDevice;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

// base data values for a device
public class DeviceEntity implements IDevice
{
    private String _id;
    private String _name;
    private String _deviceType;
    private final CompositeCoordinate _position = new CompositeCoordinate();
    private final List<DeviceCapability> _capabilities = new ArrayList<>();


    public DeviceEntity() {
    }

    public DeviceEntity(String id, String deviceType, String name) {
        _id = id;
        _deviceType = deviceType;
        _name = name;
    }


    public String getId() {
        return _id;
    }
    public String getName() {
        return _name;
    }
    public String getDeviceType() { return _deviceType; }
    public CompositeCoordinate getPosition() {
        return _position;
    }
    public List<DeviceCapability> getCapabilities() { return _capabilities; }

    public void run() {
        // noop
    }

}
