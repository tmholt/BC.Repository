package mil.don.logging.loggingservice;

import mil.don.common.logging.LoggingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingService
    implements ILoggingService {

    private Logger _logger = LoggerFactory.getLogger(this.getClass());


    public void log(LoggingEntity log) {
        //_logger.something
        if ( log == null ) return; // haha log
        System.out.println(log.toString());
    }
}
