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

package mil.don.common.conversions;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import mil.don.common.messages.Ctc.CtcMessage;
import mil.don.common.messages.Ctc.VeDropMessage;
import mil.don.common.messages.Ctc.VeTrackUpdateMessage;
import mil.don.common.messages.tcut30.DataMessage;
import mil.don.common.messages.tcut30.XYZPos;
import mil.don.common.messages.tcut30.XYZVel;

public class CtcToTcutTrackConverter
{
  public DataMessage.Track convert(CtcMessage message) {

    DataMessage.Track track = null;
    if ( message.isTrackUpdate() ) {

      VeTrackUpdateMessage.VeTrackUpdateMessageBuilder builder = new VeTrackUpdateMessage.VeTrackUpdateMessageBuilder(message);
      VeTrackUpdateMessage trackUpdateMsg = builder.build();
      if ( trackUpdateMsg != null )
      {
        /*
         * conversion code to TCUT 3
         */
        track = new DataMessage.Track();
        DataMessage.Track.PositionECEF positionECEF = new DataMessage.Track.PositionECEF();


        Integer trackId = trackUpdateMsg.getCTTrackId();
        XYZPos xyzPos = convertXyz(trackUpdateMsg.getX(), trackUpdateMsg.getY(), trackUpdateMsg.getZ());
        XYZVel xyzVel = convertVel(trackUpdateMsg.getXDot(), trackUpdateMsg.getYDot(), trackUpdateMsg.getZDot());
        positionECEF.setXYZPos(xyzPos);
        positionECEF.setXYZVel(xyzVel);

        long timeSinceMidnight = (long) (trackUpdateMsg.getTimeSinceMidnight() * 1000 * 1000);
        long midnight = Instant.now().truncatedTo(ChronoUnit.DAYS).getEpochSecond() * 1000 * 1000;
        BigInteger updateTime = BigInteger.valueOf(midnight + timeSinceMidnight);

        track.setTrackId(trackId);
        track.setEnd(false);
        track.setUpdateTime(updateTime);
        track.setPositionECEF(positionECEF);
      }

    }
    else if ( message.isTrackDrop() ) {
      VeDropMessage.VeDropMessageBuilder builder = new VeDropMessage.VeDropMessageBuilder(message);
      VeDropMessage trackDropMsg = builder.build();
      if ( trackDropMsg != null )
      {
        /*
         * conversion code to TCUT 3
         */
        track = new DataMessage.Track();

        Integer trackId = trackDropMsg.getCTTrackId();
        long timeSinceMidnight = (long) (trackDropMsg.getTimeSinceMidnight() * 1000 * 1000);
        long midnight = Instant.now().truncatedTo(ChronoUnit.DAYS).getEpochSecond() * 1000 * 1000;
        BigInteger updateTime = BigInteger.valueOf(midnight + timeSinceMidnight);

        track.setTrackId(trackId);
        track.setUpdateTime(updateTime);
        track.setEnd(true);
      }
    }

    return track;
  }

  /**
   * @param xPos,yPos,zPos ECEF Coordinates.
   *
   * @return xyzPos XYZPos Object. Contains the current position of the track.
   */
  private XYZPos convertXyz(double xPos, double yPos, double zPos)
  {
    XYZPos xyzPos = new XYZPos();
    xyzPos.setX(xPos);
    xyzPos.setY(yPos);
    xyzPos.setZ(zPos);

    return xyzPos;
  }

  /**
   * @param xVelocity,yVelocity,zVelocity ECEF Velocity.
   *
   * @return xyzVel XUZVel Object. Contains the current velocity of the track.
   */
  private XYZVel convertVel(double xVelocity, double yVelocity, double zVelocity)
  {
    XYZVel xyzPos = new XYZVel();
    xyzPos.setX(xVelocity);
    xyzPos.setY(yVelocity);
    xyzPos.setZ(zVelocity);

    return xyzPos;
  }
}
