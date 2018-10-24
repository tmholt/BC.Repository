package mil.don.common.logging;


import java.util.Date;

public class LoggingEntity {
    public Date timestamp;
    public Priority priority;
    public String source;
    public String message;

    public LoggingEntity() {
    }

    public LoggingEntity(Date ts, Priority p, String source, String message) {
        this.timestamp = ts;
        this.priority = p;
        this.source = source;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("%tr: P=%s S=%s %s", timestamp, priority, source, message);
    }
}
