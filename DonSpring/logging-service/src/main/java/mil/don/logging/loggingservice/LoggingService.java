package mil.don.logging.loggingservice;


import mil.don.common.logging.LoggingEntry;
import mil.don.common.logging.LoggingLevel;
import mil.don.common.services.ILoggingService;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class LoggingService
    implements ILoggingService
{

  private final Logger _logger = LoggerFactory.getLogger(LoggingService.class);
  private final CircularFifoQueue<LoggingEntry> _logs = new CircularFifoQueue<>(100);

  // send a log message
  public String log(LoggingEntry log) {

    if ( log == null ) return "";

    _logs.add(log);

    String msg = log.toString();
    switch ( log.getLoggingLevel() ) {
      case TRACE:
        _logger.trace(msg);
        break;
      case DEBUG:
        _logger.debug(msg);
        break;
      case INFO:
        _logger.info(msg);
        break;
      case WARNING:
        _logger.warn(msg);
        break;
      case ERROR:
        _logger.error(msg);
        break;
    }

    return log.toString();
  }

  // send a trace level logging message
  public void trace(String source, String message) {
    log(new LoggingEntry(new Date(), LoggingLevel.TRACE, source, message));
  }

  // send a debug level logging message
  public void debug(String source, String message) {
    log(new LoggingEntry(new Date(), LoggingLevel.DEBUG, source, message));

  }

  // send a info level logging message
  public void info(String source, String message) {
    log(new LoggingEntry(new Date(), LoggingLevel.INFO, source, message));
  }

  // send a warning level logging message
  public void warn(String source, String message) {
    log(new LoggingEntry(new Date(), LoggingLevel.WARNING, source, message));
  }

  // send a error level logging message
  public void error(String source, String message) {
    log(new LoggingEntry(new Date(), LoggingLevel.ERROR, source, message));
  }

  // get recent set of logged entries
  public LoggingEntry[] getRecent()
  {
    return _logs.toArray(new LoggingEntry[_logs.size()]);
  }
}
