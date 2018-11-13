package mil.don.common.status;

import java.io.Serializable;
import java.util.Date;

import mil.don.common.interfaces.IDevice;

public class DeviceStatusMessage implements IStatusMessage, Serializable
{
    private long _id;
    private Date _timestamp;
    private String _sourceDeviceName;
    private String _sourceDeviceType;
    private boolean _isOperational;


    public DeviceStatusMessage() {
    }

    public DeviceStatusMessage(long id, String sourceDeviceName,
         String sourceDeviceType,
         Date timestamp,
         boolean isOperational) {

        _id = id;
        _sourceDeviceName = sourceDeviceName;
        _sourceDeviceType = sourceDeviceType;
        _timestamp = timestamp;
        _isOperational = isOperational;
    }

    public long getId() { return _id; }
    public DeviceStatusMessage setId(long id) { this._id = id; return this; }

    public StatusType getStatusType() { return StatusType.DEVICE; }

    public String getSourceName() {
        return _sourceDeviceName;
    }
    public DeviceStatusMessage setSourceName(String sourceName) { this._sourceDeviceName = sourceName; return this; }

    public String getSourceDeviceType() {
        return _sourceDeviceType;
    }
    public DeviceStatusMessage setSourceDeviceType(String deviceType) { this._sourceDeviceType = deviceType; return this; }


    public Date getTimestamp() {
        return _timestamp;
    }
    public DeviceStatusMessage setTimestamp(Date timestamp) { this._timestamp = timestamp; return this; }

    public boolean getIsOperational() {
        return _isOperational;
    }
    public DeviceStatusMessage setIsOperational(boolean isOperational) { this._isOperational = isOperational; return this; }


    @Override
    public String toString() {
        return String.format("device.status last='%tc', id='%d', source='%s', type='%s' isop='%s'",
            _timestamp, _id, _sourceDeviceName, _sourceDeviceType, _isOperational);
    }
}
