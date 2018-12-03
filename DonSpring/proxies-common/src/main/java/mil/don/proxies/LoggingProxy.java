package mil.don.proxies;

import feign.Headers;
import mil.don.common.logging.LoggingEntry;
import mil.don.common.services.ILoggingService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name="don.service.logging") // eureka lookup
@FeignClient(name="don.service.logging", url="http://localhost:8012") // port lookup
public interface LoggingProxy extends ILoggingService
{
  @Headers("Content-Type: application/json")
  @RequestMapping(method = RequestMethod.POST, value = "/logging/log")
  String log(@RequestBody LoggingEntry log);

  // send a trace level logging message
  //@GetMapping("/logging/trace?src={s}&msg={m}")

  @RequestMapping(value = "/logging/trace")
  void trace(@RequestParam("src") String source,
                      @RequestParam("msg") String message) ;

  // send a debug level logging message
  @RequestMapping(value = "/logging/debug")
  void debug(@RequestParam("src") String source,
                    @RequestParam("msg") String message);

  // send a info level logging message
  @RequestMapping(value = "/logging/info")
  void info(@RequestParam("src") String source,
                   @RequestParam("msg") String message);

  // send a warning level logging message
  @RequestMapping(value = "/logging/warn")
  void warn(@RequestParam("src") String source,
                   @RequestParam("msg") String message);

  // send a error level logging message
  @RequestMapping(value = "/logging/error")
  void error(@RequestParam("src") String source, String message);


  @RequestMapping(value = "/logging/recent")
  LoggingEntry[] getRecent();


}
