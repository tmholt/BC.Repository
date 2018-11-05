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

import mil.don.common.status.ServiceStatus;

/*
// ApplicationListener is for in-proc event handling
@Component
public class Client1StatusListener implements ApplicationListener<StatusEvent>
{
    @Override
    public void onApplicationEvent(StatusEvent event) {
        System.out.println("Received spring custom event - " + event.getStatusType());
    }
}
 */


// don't need to do this because a bean in configuration will create it for us.
// i kind of think you could do either
@Service
public class Client1StatusListener
{

    CircularFifoQueue<ServiceStatus> _statusEvents = new CircularFifoQueue<>(100);


    public Client1StatusListener() {
        System.out.println("service status events listener up!");
    }


    @RabbitListener(queues="status-queue")
    public void receiveStatus(final ServiceStatus status) { // StatusEvent status
        System.out.println("received ServiceStatus event: " + status.toString());
        _statusEvents.add(status);
    }

    public ServiceStatus[] getRecent() {
        return _statusEvents.toArray(new ServiceStatus[_statusEvents.size()]);
    }
}