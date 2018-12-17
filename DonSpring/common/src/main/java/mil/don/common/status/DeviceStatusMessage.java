package mil.don.common.status;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mil.don.common.coordinates.CompositeCoordinate;

public class DeviceStatusMessage implements IStatusMessage, Serializable
{
    private String _id;
    private long _index = 1;
    private Date _timestamp;
    private String _sourceDeviceName;
    private String _sourceDeviceType;
    private boolean _isConnected;
    private boolean _isOperational;

    private boolean _isConnectedChanged = false;
    private boolean _isOperationalChanged = false;

    private boolean _isChanged = false;

    private final Map<String, String> _properties = new HashMap<>();
    private final CompositeCoordinate _position = new CompositeCoordinate();


    public DeviceStatusMessage() {
    }

    public DeviceStatusMessage(String id, String sourceDeviceName,
         String sourceDeviceType,
         Date timestamp,
         boolean isConnected,
         boolean isOperational) {

        _id = id;
        _sourceDeviceName = sourceDeviceName;
        _sourceDeviceType = sourceDeviceType;
        _timestamp = timestamp;
        _isConnected = isConnected;
        _isOperational = isOperational;
    }

    public String getId() { return _id; }
    public DeviceStatusMessage setId(String id) { this._id = id; return this; }

    public StatusType getStatusType() { return StatusType.DEVICE; }

    public String getSourceName() {
        return _sourceDeviceName;
    }
    public DeviceStatusMessage setSourceName(String sourceName) { this._sourceDeviceName = sourceName; return this; }

    public Map<String, String> getProperties() { return _properties; }

    public String getSourceDeviceType() {
        return _sourceDeviceType;
    }
    public DeviceStatusMessage setSourceDeviceType(String deviceType) { this._sourceDeviceType = deviceType; return this; }


    public Date getTimestamp() {
        return _timestamp;
    }
    public DeviceStatusMessage setTimestamp(Date timestamp) { this._timestamp = timestamp; return this; }

    // is operational defines if the device in question is talking and returning a valid operational status
    public boolean getIsOperational() { return _isOperational; }
    public DeviceStatusMessage setIsOperational(boolean isOperational) { this._isOperational = isOperational; return this; }

    // set one time if any of the values within the status message changed from the last time sent (other than timestamp)
    public boolean getIsChanged() { return _isChanged; }
    public DeviceStatusMessage setIsChanged(boolean isChanged) { this._isChanged = isChanged; return this; }

    // set one time if the operational state for a device changed from the last time status was sent
    public boolean getIsOperationalChanged() { return _isOperationalChanged; }
    public DeviceStatusMessage setIsOperationalChanged(boolean isOperationalChanged) { this._isOperationalChanged = isOperationalChanged; return this; }

    // is connected defines if the device is communicating to our platform wrapper
    public boolean getIsConnected() {
        return _isConnected;
    }
    public DeviceStatusMessage setIsConnected(boolean isConnected) { this._isConnected = isConnected; return this; }

    // set one time if the connected state for a device changed from the last time status was sent
    public boolean getIsConnectedChanged() {
        return _isConnectedChanged;
    }
    public DeviceStatusMessage setIsConnectedChanged(boolean isConnectedChanged) { this._isConnectedChanged = isConnectedChanged; return this; }

    public CompositeCoordinate getPosition() { return _position; }

    public long getIndex() { return _index; }
    public DeviceStatusMessage setIndex(long index) { _index = index; return this;}

    @Override
    public String toString() {
        return String.format("device.status last='%tc', idx='%d', source='%s', type='%s' op='%s' conn='%s' changed='%s'",
            _timestamp, _index, _sourceDeviceName, _sourceDeviceType, _isOperational, _isConnected, _isChanged);
    }
}
