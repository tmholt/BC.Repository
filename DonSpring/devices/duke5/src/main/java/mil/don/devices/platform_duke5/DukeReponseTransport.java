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

package mil.don.devices.platform_duke5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class DukeReponseTransport extends Thread
{
    private final Subject<byte[]> _rxReceiver;
    private int _port;


    public DukeReponseTransport(int port) {
        _rxReceiver = PublishSubject.create();
        _port = port;
    }

    public Observable<byte[]> getResponsesStream() {
        return _rxReceiver;
    }


    @Override
    public void run()
    {
        while ( !Thread.currentThread().isInterrupted() )
        {
            byte[] buf = new byte[4096];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            try ( DatagramSocket socket = new DatagramSocket(_port) )
            {
                socket.receive(packet);

                byte[] sdata = packet.getData();
                _rxReceiver.onNext(sdata);
            }
            catch ( IOException ex ) {
                System.out.println("ERROR receiving DUKE socket data: " + ex.getMessage());
            }


        }
    }

}
