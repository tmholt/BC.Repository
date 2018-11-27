package mil.don.common.status;

import java.io.Serializable;
import java.util.Date;

public class ServiceStatusMessage
    implements IStatusMessage, Serializable
{
    private String _id;
    private Date _timestamp;
    private String _sourceName;
    private boolean _isOperational;
    private long _index = 1;

    public ServiceStatusMessage() {
    }

    public ServiceStatusMessage(String id, String sourceName, Date timestamp, boolean isOperational) {
        _id = id;
        _sourceName = sourceName;
        _timestamp = timestamp;
        _isOperational = isOperational;
    }

    public String getId() { return _id; }
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

    public long getIndex() { return _index; }
    public ServiceStatusMessage setIndex(int index) { _index = index; return this; }

    @Override
    public String toString() {
        return String.format("service.status last='%tc', idx='%d', source='%s', isop='%s'",
            _timestamp, _index, _sourceName, _isOperational);
    }

}
