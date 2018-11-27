package mil.don.common.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.coordinates.CompositeCoordinate;
import mil.don.common.interfaces.IDevice;
import mil.don.common.logging.Priority;
import mil.don.common.services.ILoggingService;


// base data values and implementation for a device
public abstract class DeviceBase implements IDevice
{
    // if we don't hear from a device in this time (milliseconds) then time out
    protected static final int DEVICE_TIMEOUT_PERIOD = 10000;

    protected String _id;
    protected String _name;
    protected String _symbolCode;
    protected double _range;
    protected final CompositeCoordinate _position = new CompositeCoordinate();
    protected final List<DeviceCapability> _capabilities = new ArrayList<>();
    protected Map<String, String> _configOptions;

    protected DeviceConfiguration _deviceConfig;
    protected Priority _loggingLevel = Priority.INFO;
    protected final ILoggingService _logging;


    public DeviceBase(ILoggingService logging)
    {
        _logging = logging;
    }

    public String getId() {
        return _id;
    }
    public String getName() {
        return _name;
    }
    public String getSymbolCode() {
        return _symbolCode;
    }
    // deviceType implemented by subclass
    public double getRange() {
        return _range;
    }
    public CompositeCoordinate getPosition() {
        return _position;
    }
    public List<DeviceCapability> getCapabilities() { return _capabilities; }

    // base implementation for configure. pulls all common values from the given
    // config. if a specific device has configuration just for it, then this should
    // over overridden and then called from that implementation.
    public boolean configure(DeviceConfiguration deviceConfig) {

        if ( deviceConfig != null )
        {
            _deviceConfig = deviceConfig;

            _id = deviceConfig.getName();
            _name = deviceConfig.getName(); // are these really the same?
            _symbolCode = deviceConfig.getSymbolCode();
            _range = deviceConfig.getRange();
            _loggingLevel = deviceConfig.getLoggingLevel();

            setPositionFromConfig(deviceConfig.getPosition());
            setCommsFromConfig(deviceConfig.getComms());
            setOptionsFromConfig(deviceConfig.getOptions());
        }

        return true;
    }

    // start/stop - to be implemented by subclass
    // copy - to be implemented by subclass

    private void setPositionFromConfig(DeviceConfiguration.DeviceConfigurationPosition pos) {

    }

    private void setCommsFromConfig(DeviceConfiguration.DeviceConfigurationComms comms) {

    }

    private void setOptionsFromConfig(Map<String, String> options) {
        _configOptions = new HashMap<>(options);
    }



}
