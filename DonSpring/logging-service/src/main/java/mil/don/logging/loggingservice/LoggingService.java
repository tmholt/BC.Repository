package mil.don.logging.loggingservice;

import mil.don.common.logging.LoggingEntity;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.stereotype.Service;


@Service
public class LoggingService
    implements ILoggingService {

    //private Logger _logger = LoggerFactory.getLogger(this.getClass());
    CircularFifoQueue<LoggingEntity> _logs = new CircularFifoQueue<>(100);


    public void log(LoggingEntity log) {
        //_logger.something
        if ( log == null ) return; // haha log
        System.out.println(log.toString());
        _logs.add(log);
    }

    public LoggingEntity[] getRecent() {
        return _logs.toArray(new LoggingEntity[_logs.size()]);
    }
}
