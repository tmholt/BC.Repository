package mil.don.common.status;

import java.util.Date;

import mil.don.common.interfaces.IDevice;

public class DeviceStatusMessage implements IStatusMessage
{
    private long _id;
    private Date _timestamp;
    private String _sourceName;
    private boolean _isOperational;
    private IDevice _device;

    public DeviceStatusMessage() {
    }

    public DeviceStatusMessage(long id, String sourceName, Date timestamp, boolean isOperational, IDevice device) {
        _id = id;
        _sourceName = sourceName;
        _timestamp = timestamp;
        _isOperational = isOperational;
        _device = device;
    }

    public long getId() { return _id; }
    public StatusType getStatusType() { return StatusType.DEVICE; }

    public String getSourceName() {
        return _sourceName;
    }
    public DeviceStatusMessage setSourceName(String sourceName) { this._sourceName = sourceName; return this; }

    public Date getTimestamp() {
        return _timestamp;
    }
    public DeviceStatusMessage setTimestamp(Date timestamp) { this._timestamp = timestamp; return this; }

    public boolean getIsOperational() {
        return _isOperational;
    }
    public DeviceStatusMessage setIsOperational(boolean isOperational) { this._isOperational = isOperational; return this; }
    public IDevice getDeviceDetails() {
        return _device;
    }
    public DeviceStatusMessage setDeviceDetails(IDevice device) { this._device = device; return this; }

    @Override
    public String toString() {
        return String.format("device.status last='%tc', id='%d', source='%s', isop='%s'",
            _timestamp, _id, _sourceName, _isOperational);
    }
}
