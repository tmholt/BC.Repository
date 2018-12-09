package mil.don.masio;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import javax.annotation.PostConstruct;


@RestController
public class MasIoController {

    //@Autowired
    //private DiscoveryClient _discoveryClient;

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
