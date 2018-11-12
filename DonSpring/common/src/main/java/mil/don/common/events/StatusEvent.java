package mil.don.common.events;

import mil.don.common.status.IStatusMessage;
import mil.don.common.status.StatusType;

import org.springframework.context.ApplicationEvent;


public class StatusEvent extends ApplicationEvent {

    private IStatusMessage _status;
    private StatusType _statusType;

    public StatusEvent(String source, StatusType statusType, IStatusMessage status) {
        super(source);
        _statusType = statusType;
        _status = status;
    }

    public StatusType getStatusType() { return _statusType; }
    public IStatusMessage getStatus() { return _status; }
}
