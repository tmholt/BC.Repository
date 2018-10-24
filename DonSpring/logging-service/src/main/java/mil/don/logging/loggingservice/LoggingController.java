package mil.don.logging.loggingservice;


import mil.don.common.logging.LoggingEntity;
import mil.don.common.logging.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
public class LoggingController {

    @Autowired
    private ILoggingService _logger;

    // we could probably get away with the controller implement ILoggingService,
    // but the principle says not to.
    @RequestMapping(method = RequestMethod.POST, value = "/logging/log")
    public String log(LoggingEntity log) {
        _logger.log(log);

        return "ok";
    }

    // we could probably get away with the controller implement ILoggingService,
    // but the principle says not to.
    //@GetMapping("/logging/log?priority={p}&source={s}&msg={m}")
    @RequestMapping(value="/logging/log")
    public String log(
            @RequestParam("p") Priority p,
            @RequestParam("src") String source,
            @RequestParam("msg") String msg) {

        LoggingEntity log = new LoggingEntity(new Date(), p, source, msg);
        _logger.log(log);

        return "ok";
    }
}
