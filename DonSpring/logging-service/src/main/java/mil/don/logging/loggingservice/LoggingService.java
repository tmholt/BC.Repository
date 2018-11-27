package mil.don.logging.loggingservice;

import mil.don.common.logging.LoggingEntity;
import mil.don.common.services.ILoggingService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.stereotype.Service;


@Service
public class LoggingService
    implements ILoggingService
{

    //private Logger _logger = LoggerFactory.getLogger(this.getClass());
    CircularFifoQueue<LoggingEntity> _logs = new CircularFifoQueue<>(100);


    public String log(LoggingEntity log) {
        // TODO: _logger.something
        if ( log == null ) return "err"; // haha log
        _logs.add(log);

        String tostring = log.toString();
        System.out.println(tostring);
        return tostring;
    }

    public LoggingEntity[] getRecent()
    {
        return _logs.toArray(new LoggingEntity[_logs.size()]);
    }
}
