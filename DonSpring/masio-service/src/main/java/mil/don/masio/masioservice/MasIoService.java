

package mil.don.masio.masioservice;


import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.annotation.PostConstruct;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import mil.don.common.conversions.DeviceStatusToTcut30StatusConverter;
import mil.don.common.conversions.XmlTranslator;
import mil.don.common.devices.DetectionMessage;
import mil.don.common.messages.tcut30.DataMessage;
import mil.don.common.messages.tcut30.StatusMessage;
import mil.don.common.status.DeviceStatusMessage;
import mil.don.common.status.IStatusMessage;
import mil.don.common.status.StatusType;
import mil.don.common.utilities.GZipUtilities;
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

  // region DetectionsToMasSender class

  //
  // this is the class that will receive detections from connected devices
  // and push them to MAS
  //
  private class DetectionsToMasSender
  {

    private DatagramSocket _socket;

	  public DetectionsToMasSender(AppConfig appConfig) {
	    // TODO: load up the MAS ports that we should send to
      int port = 58822;
      SocketAddress outbound = new InetSocketAddress(port);

      try
      {
        _socket = new DatagramSocket(outbound);
      }
      catch (Exception ex) {
      }
    }

    public boolean send(final DetectionMessage detection) {
	    return false;
    }

    //
    // take this device status message and forward it along to MAS
    //
    public boolean send(final DeviceStatusMessage status) {
	    if ( status == null ) return false;

	    // convert into a TCUT3 message
	    DeviceStatusToTcut30StatusConverter converter = new DeviceStatusToTcut30StatusConverter();
      StatusMessage tcut = converter.convert(status);
      if ( tcut == null ) return false;

      // get the bytes to send
      byte[] bytes =  XmlTranslator.getInstance().encode(StatusMessage.class, tcut);

      try
      {

        // GZIP compress
        byte[] zipped = GZipUtilities.compress(bytes);

        // send to MAS
        DatagramPacket packet = new DatagramPacket(zipped, zipped.length);
        _socket.send(packet);
        return true;

      }
      catch ( IOException ex ) {
        return false;
      }

    }


  }

  // endregion

  // region MasReceiver class

  //
  // this is the class that on startup will open the defined MAS output
  // ports and receive data from these ports. it will then pass them back up
  // to the controller for processing.
  //
  private class MasReceiver {


    public MasReceiver(AppConfig appConfig) {

    }

    private final Subject<DataMessage.Threat> _rxThreats = PublishSubject.create();


    public Observable<DataMessage.Threat> getThreatsStream() {
      return _rxThreats;
    }


  }

  // endregion


  // region ThreatSender class

  //
  // this is the class that will receive messages in our internal format
  // and push them out to the appropriate endpoint (exchange or whatever)
  //
  private class ThreatSender {


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


	private MasReceiver _masReceiver;
	private ThreatSender _sender;
	private DetectionsToMasSender _masSender;
	private MasIoCountResults _counts = new MasIoCountResults();


    public MasIoService() {
        System.out.println("MasIoService ctor");
    }

    @PostConstruct
    public void initialize() {

      // create our connection to the detections rabbitmq exchange
      _detectionsExchange = new FanoutExchange("detection-events");

      // config should exist in _appConfig at this point.
      _sender = new ThreatSender();

      _masSender = new DetectionsToMasSender(_appConfig);
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

    // we only care about device status messages
    if ( status.getStatusType() != StatusType.DEVICE ) return;

    _counts.statusIn++;
    boolean sent = _masSender.send((DeviceStatusMessage)status);
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


  public MasIoCountResults getCounts() {
      return _counts;
  }

}