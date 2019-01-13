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

import mil.don.common.messages.Ctc.CtcMessage;
import mil.don.common.messages.Ctc.VeTrackerLocationMessage;
import mil.don.common.messages.tcut30.BitResultStatusE;
import mil.don.common.messages.tcut30.StatusMessage;
import mil.don.common.messages.tcut30.XYZPos;
import mil.don.common.messages.tcut30.XYZVel;

public class CtcToTcutStatusConverter
{
  /**
   * @param message Message to be converted. Convert VeMessage into a TCUT 3.0 StatusMessage.Status.
   *
   * @return status TCUT 3.0 StatusMessage.Status. Populated child node of StatusMessage containing sensor (radar) status data.
   */
  public StatusMessage.Status convertCtcToStatus(CtcMessage message)
  {
    StatusMessage.Status status = null;

    if ( message.isTrackerLocation() )
    {
      status = new StatusMessage.Status();

      VeTrackerLocationMessage.VeTrackerLocationMessageBuilder builder =
          new VeTrackerLocationMessage.VeTrackerLocationMessageBuilder(message);

      VeTrackerLocationMessage ve = builder.build();


      // spacial factors
      StatusMessage.Status.SpatialFactors spatialFactors = new StatusMessage.Status.SpatialFactors();
      //XYZPos reference = convertXyz(ve.getX(), ve.getY(), ve.getZ());
      //spatialFactors.setReferencePos(reference);
      status.setSpatialFactors(spatialFactors);

      XYZPos emplacement = convertXyz(ve.getX(), ve.getY(), ve.getZ());
      spatialFactors.setEmplacement(emplacement);

      status.setOverallStatus(BitResultStatusE.NORMAL);
      status.setMission("OPMode");
    }

    return status;
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
