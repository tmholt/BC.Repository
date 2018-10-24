package mil.don.common.devices;

import java.util.Date;

public class DeviceStatus {
    private Date _lastHeard;
    private boolean _isOnline;
    private DeviceEntity _device;

    public DeviceStatus() {
    }

    public DeviceStatus(Date lastHeard, boolean isOnline, DeviceEntity device) {
        _lastHeard = lastHeard;
        _isOnline = isOnline;
        _device = device;
    }

    public Date getLastHeard() {
        return _lastHeard;
    }

    public DeviceStatus setLastHeard(Date _lastHeard) {
        this._lastHeard = _lastHeard;
        return this;
    }

    public boolean getIsOnline() {
        return _isOnline;
    }

    public DeviceStatus setIsOnline(boolean _isOnline) {
        this._isOnline = _isOnline;
        return this;
    }

    public DeviceEntity getDeviceDetails() {
        return _device;
    }

    public DeviceStatus setDeviceDetails(DeviceEntity _device) {
        this._device = _device;
        return this;
    }
}
