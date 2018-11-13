/*-------------------------------------------------------------------------------------------------
| *** UNCLASSIFIED ***
|--------------------------------------------------------------------------------------------------
| U.S. Army Research, Development, and Engineering Command
| Aviation and Missile Research, Development, and Engineering Center
| Software Engineering Directorate, Redstone Arsenal, AL
|--------------------------------------------------------------------------------------------------
| Export-Control Act Warning: Warning - This Document contains technical data whose export is
| restricted by the Arms Export Control Act (Title 22, U.S.C., Sec 2751, et seq) or the Export
| Administration Act of 1979, as amended, Title 50, U.S.C., App. 2401 et seq. Violations of these
| export laws are subject to severe criminal penalties. Disseminate in accordance with provisions
| of DoD Directive 5230.25.
|--------------------------------------------------------------------------------------------------
| Distribution Statement C:  Distribution authorized to U.S. Government Agencies and their
| contractors, Export Controlled, Critical Technology, 13 Feb 2017. Other request for this document
| shall be referred to U.S. Army Aviation and Missile Research Development and Engineering Center
| Software Engineering Directorate, Attn: RDMR-BAW, Redstone Arsenal, AL 35898.
--------------------------------------------------------------------------------------------------*/

package mil.don.client1;


import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

import javax.annotation.PostConstruct;

import mil.don.common.devices.DetectionMessage;
import mil.don.common.status.IStatusMessage;
import mil.don.common.status.ServiceStatus;


// don't need to do this because a bean in configuration will create it for us.
// i kind of think you could do either
@Service
public class Client1StatusListener
{

    // save the last 100 status events that we receive
    CircularFifoQueue<IStatusMessage> _statusEvents = new CircularFifoQueue<>(100);


    // save the last 100 detection events that we receive
    CircularFifoQueue<DetectionMessage> _detectionEvents = new CircularFifoQueue<>(100);


    public Client1StatusListener() {
        System.out.println("service status events listener up!");
    }

    @PostConstruct
    public void initialize() {
    }



    @RabbitListener(queues="#{statusMessagesQueue.name}")
    public void receiveStatus(final IStatusMessage status) {
        System.out.println("received status event: " + status.toString());
        _statusEvents.add(status);
    }

    @RabbitListener(queues="#{detectionMessagesQueue.name}")
    public void receiveDetections(final DetectionMessage detection) {
        System.out.println("received detection event: " + detection.toString());
        _detectionEvents.add(detection);
    }

    public IStatusMessage[] getRecentStatusEvents() {
        return _statusEvents.toArray(new IStatusMessage[_statusEvents.size()]);
    }


    public DetectionMessage[] getRecentDetectionEvents() {
        return _detectionEvents.toArray(new DetectionMessage[_detectionEvents.size()]);
    }
}