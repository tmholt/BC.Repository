package mil.don.common.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.coordinates.CompositeCoordinate;
import mil.don.common.interfaces.IDevice;


// base data values for a device
public abstract class DeviceBase implements IDevice, Cloneable
{
    protected String _id;
    protected String _name;
    protected String _symbolCode;
    protected double _range;
    protected final CompositeCoordinate _position = new CompositeCoordinate();
    protected final List<DeviceCapability> _capabilities = new ArrayList<>();
    protected Map<String, String> _configOptions;
    private Thread _runThread;

    protected DeviceConfiguration _deviceConfig;



    public DeviceBase() {
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

    public Thread getRunThread()
    {
        return _runThread;
    }
    public void setRunThread(Thread _runThread)
    {
        this._runThread = _runThread;
    }


    public boolean configure(DeviceConfiguration deviceConfig) {

        if ( deviceConfig != null )
        {
            _deviceConfig = deviceConfig;

            _id = deviceConfig.getName();
            _name = deviceConfig.getName(); // are these really the same?
            _symbolCode = deviceConfig.getSymbolCode();
            _range = deviceConfig.getRange();

            setPositionFromConfig(deviceConfig.getPosition());
            setCommsFromConfig(deviceConfig.getComms());
            setOptionsFromConfig(deviceConfig.getOptions());
        }

        return true;
    }

    // runnable - to be implemented by subclass

    // cloneable (kinda)
    public IDevice clone() {
        try
        {
            return (IDevice)super.clone();
        }
        catch ( CloneNotSupportedException e )
        {
            return null;
        }
    }

    private void setPositionFromConfig(DeviceConfiguration.DeviceConfigurationPosition pos) {

    }

    private void setCommsFromConfig(DeviceConfiguration.DeviceConfigurationComms comms) {

    }

    private void setOptionsFromConfig(Map<String, String> options) {
        _configOptions = new HashMap<>(options);
    }



}
