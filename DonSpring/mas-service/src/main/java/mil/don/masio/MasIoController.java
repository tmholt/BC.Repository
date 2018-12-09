package mil.don.masio;



import mil.don.common.devices.DetectionMessage;
import mil.don.common.devices.DeviceCommandBase;
import mil.don.common.interfaces.IDevice;
import mil.don.common.status.IStatusMessage;
import mil.don.masio.configuration.AppConfig;
import mil.don.proxies.DeviceMgrProxy;
import mil.don.proxies.LoggingProxy;


import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;


@RestController
public class MasIoController {

    @Autowired
    private DiscoveryClient _discoveryClient;



    @Autowired
    private MasIoService _masIoService;
	
    @Autowired
	  private Exchange _detectionsExchange;

    // rabbitmq template
    @Autowired
    private RabbitTemplate _rabbitTemplate;
	

    @PostConstruct
    public void initialize() {
    }


	// plan is to add threats received from MAS back onto the detections exchange,
	// with a different topic.
    void exchangeThreatEvent(Threat threat) {
        _rabbitTemplate.convertAndSend(_detectionsExchange.getName(), THREAT_ROUTING_KEY, threat);
        System.out.println("MasIoController::exchangeThreatEvent: sent threat event: " + threat.toString());
    }


	@RequestMapping("/masio/counts")
    public MasIoCountResults getMessageCounts() {
        return new MasIoCountResults();
    }

}
