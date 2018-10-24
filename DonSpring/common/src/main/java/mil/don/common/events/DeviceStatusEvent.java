package mil.don.common.events;

import mil.don.common.devices.DeviceStatus;
import org.springframework.context.ApplicationEvent;


public class DeviceStatusEvent extends ApplicationEvent {

    private DeviceStatus _status;

    public DeviceStatusEvent(String source, DeviceStatus status) {
        super(source);
        _status = status;
    }

    public DeviceStatus getStatus() { return _status; }
}
