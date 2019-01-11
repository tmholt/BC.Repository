package mil.don.common.devices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import mil.don.common.configuration.DeviceConfiguration;
import mil.don.common.coordinates.CompositeCoordinate;
import mil.don.common.logging.LoggingLevel;
import mil.don.common.logging.StdoutLogger;
import mil.don.common.messages.tcut30.StatusMessage;
import mil.don.common.services.ILoggingService;
import mil.don.common.status.DeviceStatusMessage;


// base data values and implementation for a device
// NOTE: this is not abstract, as this is being used in a service contract by
// the device manager service. Maybe there should be a different class for the
// public device-manager-service API that is not the base class for actual devices.
public class DeviceBase implements IDevice, Serializable
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
    protected LoggingLevel _loggingLevel = LoggingLevel.INFO;
    protected final transient ILoggingService _logging;

    // outbound stream of device status events
    protected final transient Subject<StatusMessage> _rxStatus;



    public DeviceBase()
    {
      this(new StdoutLogger());
    }

    public DeviceBase(ILoggingService logging)
    {
        _logging = logging;
        _rxStatus = PublishSubject.create();
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

    // deviceType should be implemented by subclass
    public String getDeviceType() { return "UNKNOWN"; }

    public double getRange() {
        return _range;
    }
    public CompositeCoordinate getPosition() {
        return _position;
    }
    public List<DeviceCapability> getCapabilities() { return _capabilities; }

    // outbound stream of status events
    public Observable<StatusMessage> getStatusStream() { return _rxStatus; }


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

    // called at initialization. start anything for this device that needs
    // to be started. NOTE that the caller is NOT wrapping this in a thread,
    // as maybe this device doesn't need any. So it is up to the individual
    // device to decide what threading is needed.
    // should be @Overridden by actual device
    @Override
    public boolean start() {
        return false;
    }

    // stop operation - called on termination of device manager service.
    // close any threads and connections to actual device.
    // should be @Overridden by actual device implementation
    @Override
    public boolean stop()
    {
      return false;
    }

    // cloneable (kinda)
    // need to decide how to actually do this. can't reuse in subclasses
    // currently, and @Override would have to copy code.
    public DeviceBase copy() {
      DeviceBase d = new DeviceBase(_logging);
      copyBase(d);
      return d;
    }

    protected void copyBase(DeviceBase to) {
      to._id = _id;
      to._name = _name;
      to._symbolCode = _symbolCode;
      to._range = _range;
      to._position.set(_position);
      // .. more
    }

    // ability to send a command to a particular device
    // should be overridden for a specific device type
    @Override
    public boolean executeDeviceCommand(DeviceCommandBase command) {
        return false;
    }

    // start/stop - to be implemented by subclass
    // copy - to be implemented by subclass

    //
    // load up the position for this device from configuration
    //
    protected void setPositionFromConfig(DeviceConfiguration.DeviceConfigurationPosition pos) {

    }

    //
    // load up any URIs for comms from configuration
    //
    protected void setCommsFromConfig(DeviceConfiguration.DeviceConfigurationComms comms) {

    }

    // is our current logging level (from config) less than or equal to the given value?
    protected boolean loggingLevelIs(LoggingLevel p) {
        LoggingLevel defined = _deviceConfig.getLoggingLevel();
        return ( defined.ordinal() <= p.ordinal() );
    }

    //
    // load any configuration values specific to this device. @Override this for a specific
    // device type.
    //
    protected void setOptionsFromConfig(Map<String, String> options) {
        _configOptions = new HashMap<>(options);
    }



}
