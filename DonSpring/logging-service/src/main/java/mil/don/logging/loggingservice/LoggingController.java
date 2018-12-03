package mil.don.logging.loggingservice;

import mil.don.common.logging.LoggingEntry;
import mil.don.common.services.ILoggingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
public class LoggingController implements ILoggingService
{

  @Autowired
  private ILoggingService _logger;

  // send a complete log entry via post
  @RequestMapping(method = RequestMethod.POST, value = "/logging/log")
  public String log(@RequestBody LoggingEntry log)
  {
    if ( log == null ) return "ERROR";
    return _logger.log(log);
  }

  // send a trace level logging message
  //@GetMapping("/logging/trace?src={s}&msg={m}")
  @RequestMapping(value = "/logging/trace")
  @ResponseStatus(HttpStatus.OK)
  public void trace(@RequestParam("src") String source,
                      @RequestParam("msg") String message) {
    _logger.trace(source, message);
  }

  // send a debug level logging message
  @RequestMapping(value = "/logging/debug")
  @ResponseStatus(HttpStatus.OK)
  public void debug(@RequestParam("src") String source,
                    @RequestParam("msg") String message) {
    _logger.debug(source, message);
  }

  // send a info level logging message
  @RequestMapping(value = "/logging/info")
  @ResponseStatus(HttpStatus.OK)
  public void info(@RequestParam("src") String source,
                   @RequestParam("msg") String message) {
    _logger.info(source, message);
  }

  // send a warning level logging message
  @RequestMapping(value = "/logging/warn")
  @ResponseStatus(HttpStatus.OK)
  public void warn(@RequestParam("src") String source,
                   @RequestParam("msg") String message) {
    _logger.warn(source, message);
  }

  // send a error level logging message
  @RequestMapping(value = "/logging/error")
  @ResponseStatus(HttpStatus.OK)
  public void error(@RequestParam("src") String source,
                    @RequestParam("msg") String message) {
    _logger.error(source, message);
  }


  @RequestMapping(value = "/logging/recent")
  public LoggingEntry[] getRecent()
  {
    return _logger.getRecent();
  }
}
