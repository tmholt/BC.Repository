package mil.don.common.status;

import java.util.Date;

import mil.don.common.devices.DeviceEntity;

public class DeviceStatus implements IStatusData {

    private Date _lastHeard;
    private String _sourceName;
    private boolean _isOperational;
    private DeviceEntity _device;

    public DeviceStatus() {
    }

    public DeviceStatus(String sourceName, Date lastHeard, boolean isOperational, DeviceEntity device) {
        _sourceName = sourceName;
        _lastHeard = lastHeard;
        _isOperational = isOperational;
        _device = device;
    }

    public StatusType getStatusType() { return StatusType.DEVICE; }

    public String getSourceName() {
        return _sourceName;
    }
    public DeviceStatus setSourceName(String sourceName) { this._sourceName = sourceName; return this; }

    public Date getLastHeard() {
        return _lastHeard;
    }
    public DeviceStatus setLastHeard(Date _lastHeard) { this._lastHeard = _lastHeard; return this; }

    public boolean getIsOperational() {
        return _isOperational;
    }
    public DeviceStatus setIsOperational(boolean isOperational) { this._isOperational = isOperational; return this; }

    public DeviceEntity getDeviceDetails() {
        return _device;
    }
    public DeviceStatus setDeviceDetails(DeviceEntity _device) { this._device = _device; return this; }
}
