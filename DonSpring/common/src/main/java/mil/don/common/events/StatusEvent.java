package mil.don.common.events;

import mil.don.common.status.DeviceStatus;
import mil.don.common.status.IStatusData;
import mil.don.common.status.StatusType;

import org.springframework.context.ApplicationEvent;


public class StatusEvent extends ApplicationEvent {

    private IStatusData _status;
    private StatusType _statusType;

    public StatusEvent(String source, StatusType statusType, IStatusData status) {
        super(source);
        _statusType = statusType;
        _status = status;
    }

    public StatusType getStatusType() { return _statusType; }
    public IStatusData getStatus() { return _status; }
}
