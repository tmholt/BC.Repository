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
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import mil.don.common.devices.DetectionMessage;
import mil.don.common.messages.tcut30.DataMessage;
import mil.don.common.messages.tcut30.StatusMessage;
import mil.don.common.status.IStatusMessage;
import mil.don.common.status.ServiceStatusMessage;


// don't need to do this because a bean in configuration will create it for us.
// i kind of think you could do either
@Service
public class Client1StatusListener
{

    // save the last 100 device status events that we receive
    CircularFifoQueue<StatusMessage> _deviceStatusEvents = new CircularFifoQueue<>(100);

    // save the last 100 system status events that we receive
    CircularFifoQueue<ServiceStatusMessage> _systemStatusEvents = new CircularFifoQueue<>(100);


    // save the last 100 detection events that we receive
    CircularFifoQueue<DataMessage> _detectionEvents = new CircularFifoQueue<>(100);


    public Client1StatusListener() {
        System.out.println("service status events listener up!");
    }

    @PostConstruct
    public void initialize() {
    }



    @RabbitListener(queues="#{statusMessagesQueue.name}")
    public void receiveDeviceStatus(final StatusMessage status) {
        System.out.println("received status event: " + status.toString());
      _deviceStatusEvents.add(status);
    }

    @RabbitListener(queues="#{statusMessagesQueue.name}")
    public void receiveSystemStatus(final ServiceStatusMessage status) {
      System.out.println("received status event: " + status.toString());
      _systemStatusEvents.add(status);
    }

    @RabbitListener(queues="#{detectionMessagesQueue.name}")
    public void receiveDetections(final DataMessage detection) {
        System.out.println("received detection event: " + detection.toString());
        _detectionEvents.add(detection);
    }

    public StatusMessage[] getRecentDeviceStatusEvents() {
        return _deviceStatusEvents.toArray(new StatusMessage[_deviceStatusEvents.size()]);
    }

    public ServiceStatusMessage[] getRecentSystemStatusEvents() {
      return _systemStatusEvents.toArray(new ServiceStatusMessage[_systemStatusEvents.size()]);
    }

    public DataMessage[] getRecentDetectionEvents() {
        return _detectionEvents.toArray(new DataMessage[_detectionEvents.size()]);
    }
}