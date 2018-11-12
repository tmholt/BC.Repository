package mil.don.common.status;

import java.io.Serializable;
import java.util.Date;

public class ServiceStatusMessage
    implements IStatusMessage, Serializable
{
    private long _id;
    private Date _timestamp;
    private String _sourceName;
    private boolean _isOperational;


    public ServiceStatusMessage() {
    }

    public ServiceStatusMessage(long id, String sourceName, Date timestamp, boolean isOperational) {
        _id = id;
        _sourceName = sourceName;
        _timestamp = timestamp;
        _isOperational = isOperational;
    }

    public long getId() { return _id; }
    public StatusType getStatusType() { return StatusType.SERVICE; }

    public String getSourceName() {
        return _sourceName;
    }
    public ServiceStatusMessage setSourceName(String sourceName) { this._sourceName = sourceName; return this; }

    public Date getTimestamp() {
        return _timestamp;
    }
    public ServiceStatusMessage setTimestamp(Date timestamp) { this._timestamp = timestamp; return this; }

    public boolean getIsOperational() {
        return _isOperational;
    }
    public ServiceStatusMessage setIsOperational(boolean isOperational) { this._isOperational = isOperational; return this; }

    @Override
    public String toString() {
        return String.format("service.status last='%tc', id='%d', source='%s', isop='%s'",
            _timestamp, _id, _sourceName, _isOperational);
    }

}
