

package mil.don.masio;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import io.reactivex.subjects.Subject;
import mil.don.common.devices.DetectionMessage;
import mil.don.common.status.IStatusMessage;
import mil.don.masio.configuration.AppConfig;
import mil.don.proxies.LoggingProxy;


//
// this is the overall manager class for the stream of operations related
// to working with MAS. It will receive MAS messages, convert them
// into our common format and then put them back out on a rabbit exchange
// for clients to receive and deal with.
//
@Service
public class MasIoService {

  // region MasIoCountResults class

	class MasIoCountResults implements Serializable {
		public int detectionsIn;
		public int detectionsInSent;
		public int statusIn;
		public int statusInSent;
		public int statusOut;
		public int detectionsOut; //??
		public int threatsOut;
	}

	// endregion

  // region MasReceiver class

  //
  // this is the class that on startup will open the defined MAS output
  // ports and receive data from these ports. it will then pass them back up
  // to the controller for processing.
  //
  class MasReceiver {

    private final Subject<MasThreat> _rxThreats;


  }

  // endregion

  // region MasMessageConverter class

  //
  // this is the class that will receive the native messages from
  // MAS and convert them into our internal format for transfer.
  //
  class MasMessageConverter {

  }

  // endregion

  // region MasSender class

  //
  // this is the class that will receive messages in our internal format
  // and push them out to the appropriate endpoint (exchange or whatever)
  //
  class MasSender {

  }

  // endregion

  private final String THREAT_ROUTING_KEY = "system.threat";

  // this is the configuration for this mas-io service
  @Autowired
  private AppConfig _appConfig;

  @Autowired
  private LoggingProxy _logging;


  private MasMessageConverter _converter;
	private MasReceiver _receiver;
	private MasSender _sender;
	private MasIoCountResults _counts = new MasIoCountResults();


    public MasIoService() {
        System.out.println("MasIoService ctor");
    }

    @PostConstruct
    public void initialize() {
        // TODO: read ports from config
        _converter = new MasMessageConverter();
        _sender = new MasSender();

        _receiver = new MasReceiver();
        _receiver.onThreatStream().subscribe(onThreat);
    }



    @RabbitListener(queues="#{statusMessagesQueue.name}")
    public void receiveStatus(final IStatusMessage status) {
        System.out.println("received inbound status event: " + status.toString());
		
		_counts.statusIn++;
		data = _converter.convert(status);
		if ( data != null ) {
			_sender.sendStatus(data);
			_counts.statusInSent++;
		}
		
    }

    @RabbitListener(queues="#{detectionMessagesQueue.name}")
    public void receiveDetections(final DetectionMessage detection) {
        System.out.println("received inbound detection event: " + detection.toString());
		
		_counts.detectionsIn++;
		data = _converter.convert(detection);
		if ( data != null ) {
			_sender.sendDetection(data);
			_counts.detectionsInSent++;
		}
    }
	
	// received a threat from MAS
	private void onThreat(Threat threat) {
		System.out.println("received outbound MAS threat event: " + threat.toString());
		_counts.threatsOut++;
		
		// convert to internal format?
		// outbound == TCUT3
		
		exchangeThreat(threat);
	}


}