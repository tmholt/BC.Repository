package mil.don.masio.masioservice;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import mil.don.masio.masioservice.configuration.AppConfig;


@RestController
public class MasIoController {

    // this is the configuration for this mas-io service
    @Autowired
    private AppConfig _appConfig;


    @Autowired
    private MasIoService _masIoService;
	

    @PostConstruct
    public void initialize() {
    }


	@RequestMapping("/masio/counts")
    public MasIoService.MasIoCountResults getMessageCounts() {
        return _masIoService.getCounts();
    }

}
