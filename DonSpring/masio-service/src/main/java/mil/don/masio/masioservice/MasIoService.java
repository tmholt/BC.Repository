

package mil.don.masio.masioservice;


import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import mil.don.common.devices.DetectionMessage;
import mil.don.common.messages.tcut30.DataMessage;
import mil.don.common.status.IStatusMessage;
import mil.don.masio.masioservice.configuration.AppConfig;
import mil.don.proxies.LoggingProxy;


//
// this is the overall manager class for the stream of operations related
// to working with MAS. It will :
//    receive status and detections from devices in our system
//    convert and send these messages to MAS
//    receive MAS messages
//    convert MAS messages into our internal format
//    push the new messages back out to the intended customers (rabbit exchange, whatever)
//
// into our common format and then put them back out on a rabbit exchange
// for clients to receive and deal with.
//
// NOTE: right now the service is the DetectionsReceiver, rather than having a subclass do this.
// It just seemed more straightforward, but this could be changed.
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

  // region MasDetectionsSender class

  //
  // this is the class that will receive detections from connected devices
  // and push them to MAS
  //
  class MasDetectionsSender {

	  public MasDetectionsSender(AppConfig appConfig) {
    }

    public boolean send(final DetectionMessage detection) {
	    return false;
    }

    public boolean send(final IStatusMessage status) {
      return false;
    }


  }

  // endregion

  // region MasReceiver class

  //
  // this is the class that on startup will open the defined MAS output
  // ports and receive data from these ports. it will then pass them back up
  // to the controller for processing.
  //
  class MasReceiver {


    public MasReceiver(AppConfig appConfig) {

    }

    private final Subject<DataMessage.Threat> _rxThreats = PublishSubject.create();


    public Observable<DataMessage.Threat> getThreatsStream() {
      return _rxThreats;
    }


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

  // region ThreatSender class

  //
  // this is the class that will receive messages in our internal format
  // and push them out to the appropriate endpoint (exchange or whatever)
  //
  class ThreatSender {


    // plan is to add threats received from MAS back onto the detections exchange,
    // with a different topic.
    void exchangeThreatEvent(DataMessage.Threat threat) {
      _rabbitTemplate.convertAndSend(_detectionsExchange.getName(), THREAT_ROUTING_KEY, threat);
      System.out.println("MasIoController::exchangeThreatEvent: sent threat event: " + threat.toString());
    }



    public void send(DataMessage.Threat threat) {

    }

  }

  // endregion

  private final String THREAT_ROUTING_KEY = "system.threat";

  // this is the configuration for this mas-io service
  @Autowired
  private AppConfig _appConfig;

  @Autowired
  private LoggingProxy _logging;

  // the plan is to push threats onto this exchange with a threat topic
  private Exchange _detectionsExchange;

  // rabbitmq template
  @Autowired
  private RabbitTemplate _rabbitTemplate;


  private MasMessageConverter _converter;
	private MasReceiver _masReceiver;
	private ThreatSender _sender;
	private MasDetectionsSender _masSender;
	private MasIoCountResults _counts = new MasIoCountResults();


    public MasIoService() {
        System.out.println("MasIoService ctor");
    }

    @PostConstruct
    public void initialize() {

      // create our connection to the detections rabbitmq exchange
      _detectionsExchange = new FanoutExchange("detection-events");

      // config should exist in _appConfig at this point.
        _converter = new MasMessageConverter();
        _sender = new ThreatSender();

        _masSender = new MasDetectionsSender(_appConfig);
        _masReceiver = new MasReceiver(_appConfig);

      // _masReceiver will fire out threats as they are received
      // do the same for detections / tracks
      Observable<DataMessage.Threat> threats = _masReceiver.getThreatsStream();
      if ( threats != null )
      {
        threats.subscribe(
            // on next
            (DataMessage.Threat threat) -> {
              // convert
              _sender.send(threat);
            },
            // on error
            (Throwable error) -> {
              System.out.println("MasIoService::threat subscription ERROR in stream handling: " + error);
            },
            // on completion
            () -> {
              System.out.println("MasIoService::threat subscription STREAM COMPLETE");
            });
      }

    }

  @RabbitListener(queues = "#{statusMessagesQueue.name}")
  public void receiveStatus(final IStatusMessage status)
  {
    System.out.println("received inbound status event: " + status.toString());

    _counts.statusIn++;
    boolean sent = _masSender.send(status);
    if ( sent ) _counts.statusInSent++;

  }

  @RabbitListener(queues = "#{detectionMessagesQueue.name}")
  public void receiveDetections(final DetectionMessage detection)
  {
    System.out.println("received inbound detection event: " + detection.toString());

    _counts.detectionsIn++;
    boolean sent = _masSender.send(detection);
    if ( sent ) _counts.detectionsInSent++;
  }





  public MasIoCountResults getCounts() { return _counts; }

}