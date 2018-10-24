package mil.don.proxies;

import mil.don.common.logging.LoggingEntity;
import mil.don.common.logging.Priority;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="logging-service") // eureka lookup
public interface LoggingProxy {

    @RequestMapping(method = RequestMethod.POST, value = "/logging/log")
    String log(LoggingEntity log);


    // we could probably get away with the controller implement ILoggingService,
    // but the principle says not to.
    //@GetMapping("/logging/log?priority={p}&source={s}&msg={m}")
    @RequestMapping(value="/logging/log")
    String log(
            @RequestParam("p") Priority p,
            @RequestParam("src") String source,
            @RequestParam("msg") String msg);
}
