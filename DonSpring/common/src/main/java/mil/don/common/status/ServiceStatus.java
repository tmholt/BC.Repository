package mil.don.common.status;

import java.io.Serializable;
import java.util.Date;

import mil.don.common.devices.DeviceEntity;

public class ServiceStatus
    implements IStatusData, Serializable
{
    private Date _lastHeard;
    private String _sourceName;
    private boolean _isOperational;


    public ServiceStatus() {
    }

    public ServiceStatus(String sourceName, Date lastHeard, boolean isOperational) {
        _sourceName = sourceName;
        _lastHeard = lastHeard;
        _isOperational = isOperational;
    }

    public StatusType getStatusType() { return StatusType.SERVICE; }

    public String getSourceName() {
        return _sourceName;
    }
    public ServiceStatus setSourceName(String sourceName) { this._sourceName = sourceName; return this; }

    public Date getLastHeard() {
        return _lastHeard;
    }
    public ServiceStatus setLastHeard(Date _lastHeard) { this._lastHeard = _lastHeard; return this; }

    public boolean getIsOperational() {
        return _isOperational;
    }
    public ServiceStatus setIsOperational(boolean isOperational) { this._isOperational = isOperational; return this; }

    @Override
    public String toString() {
        return String.format("service.status last='%s', source='%s', isop='%s'",
            _lastHeard, _sourceName, _isOperational);
    }

}
