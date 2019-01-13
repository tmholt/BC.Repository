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

import java.util.ArrayList;
import java.util.List;

import mil.don.common.messages.tcut21.EWMessage;
import mil.don.common.messages.tcut30.Affiliation;
import mil.don.common.messages.tcut30.BitResultStatusE;
import mil.don.common.messages.tcut30.DataMessage;
import mil.don.common.messages.tcut30.EwMode;
import mil.don.common.messages.tcut30.LineOfBearing;
import mil.don.common.messages.tcut30.PosCovariance;
import mil.don.common.messages.tcut30.StatusMessage;
import mil.don.common.messages.tcut30.SystemTypeE;
import mil.don.common.messages.tcut30.XYZPos;

public class Tcut21ToTcut30Converter
{
  private static final String REV = "3.0";


  public Tcut3DataAndStatusMessageWrapper convert(EWMessage message)
  {
    Tcut3DataAndStatusMessageWrapper result = new Tcut3DataAndStatusMessageWrapper();
    List<StatusMessage> statusMessage = buildStatusMessage(message);
    List<DataMessage> dataMessage = buildDataMessage(message);
    result.setStatusMessage(statusMessage);
    result.setDataMessage(dataMessage);
    return result;
  }

  private List<StatusMessage> buildStatusMessage(EWMessage message)
  {
    List<StatusMessage> finalResult = new ArrayList<>();
    if (message != null)
    {
      for (EWMessage.DataReport dataReport : message.getDataReport())
      {
        for (EWMessage.DataReport.SystemStatus status : dataReport.getSystemStatus())
        {
          StatusMessage statusMessage = new StatusMessage();
          statusMessage.setRevision(REV);
          statusMessage.setSourceSystem(message.getSourceSystem());
          statusMessage.setTime(dataReport.getTime());
          statusMessage.setTimeIsValid(dataReport.isTimeIsValid());
          statusMessage.setMsgCount(dataReport.getMsgCount());
          statusMessage.setSourceType(SystemTypeE.EW);

          StatusMessage.Status newStatus = new StatusMessage.Status();
          newStatus.setMission("Converter Default String");

          if (status.getOverallStatus() != null)
          {
            newStatus.setOverallStatus(BitResultStatusE.fromValue(status.getOverallStatus().value()));
          }
          newStatus.setSwVersion(status.getSwVersion());
          if (status.getSystemState() != null)
          {
            newStatus.setSystemState(EwMode.fromValue(status.getSystemState().value()));
          }

          StatusMessage.Status.SpatialFactors spatialFactors = new StatusMessage.Status.SpatialFactors();
          spatialFactors.setGpsLocked(status.isGpsIsLocked());
          if (status.getXYZPos() != null)
          {
            mil.don.common.messages.tcut21.XYZPos oldXyzPos = status.getXYZPos();
            XYZPos newXyzPos = new XYZPos();
            newXyzPos.setX(oldXyzPos.getX());
            newXyzPos.setY(oldXyzPos.getY());
            newXyzPos.setZ(oldXyzPos.getZ());
            spatialFactors.setEmplacement(newXyzPos);
            //spatialFactors.setReferencePos(newXyzPos);
          }
          newStatus.setSpatialFactors(spatialFactors);
          statusMessage.setStatus(newStatus);
          finalResult.add(statusMessage);
        }
      }
    }
    return finalResult;
  }

  private List<DataMessage> buildDataMessage(EWMessage message)
  {
    List<DataMessage> finalResult = new ArrayList<>();
    if (message != null)
    {
      for (EWMessage.DataReport dataReport : message.getDataReport())
      {
        //added for the when it is only a status message
        if (dataReport.getEWTrack().size() == 0)
        {
          break;
        }
        DataMessage dataMessage = new DataMessage();
        dataMessage.setRevision(REV);
        dataMessage.setSourceSystem(message.getSourceSystem());
        if (dataReport.getTime() != null)
        {
          dataMessage.setTime(dataReport.getTime());
        }
        dataMessage.setTimeIsValid(dataReport.isTimeIsValid());
        dataMessage.setMsgCount(dataReport.getMsgCount());
        dataMessage.setSourceType(SystemTypeE.EW);
        for (EWMessage.DataReport.EWTrack track : dataReport.getEWTrack())
        {
          DataMessage.Track dataMessageTrack = new DataMessage.Track();

          if (track.getAffiliation() != null)
          {
            dataMessageTrack.setAffiliation(Affiliation.fromValue(track.getAffiliation().value()));
          }
          dataMessageTrack.setTrackId(track.getTrackId());
          if (track.getUpdateTime() != null)
          {
            dataMessageTrack.setUpdateTime(track.getUpdateTime());
          }
          if (track.isEndTrack() != null)
          {
            dataMessageTrack.setEnd(track.isEndTrack());
          }
          //Line of bearing
          LineOfBearing lob = new LineOfBearing();
          if (track.getAzimuth() != null)
          {
            lob.setAzimuth(track.getAzimuth());
          }
          if (track.getAzimuthErr() != null)
          {
            lob.setAzimuthErr(track.getAzimuthErr());
          }
          else
          {
            lob.setAzimuthErr(10.0f);
          }
          if (track.getElevation() != null)
          {
            lob.setElevation(track.getElevation());
          }
          if (track.getElevationErr() != null)
          {
            lob.setElevationErr(track.getElevationErr());
          }
          else
          {
            lob.setElevationErr(15.0f);
          }
          lob.setMinRange(10.0f);
          lob.setMaxRange(2000.f);
          dataMessageTrack.setLineOfBearing(lob);

          //XYZ Position
          if (track.getXYZPos() != null)
          {
            mil.don.common.messages.tcut21.XYZPos oldXyzPos = track.getXYZPos();
            XYZPos newXyzPos = new XYZPos();

            newXyzPos.setX(oldXyzPos.getX());
            newXyzPos.setY(oldXyzPos.getY());
            newXyzPos.setZ(oldXyzPos.getZ());

            dataMessageTrack.setPositionECEF(new DataMessage.Track.PositionECEF());
            dataMessageTrack.getPositionECEF().setXYZPos(newXyzPos);
          }
          //Covariance
          if (track.getPosError() != null)
          {
            PosCovariance covariance = new PosCovariance();
            mil.don.common.messages.tcut21.PosError posError = track.getPosError();
            covariance.setXx(posError.getXx());
            covariance.setXy(posError.getXy());
            covariance.setXz(posError.getXz());
            covariance.setYy(posError.getYy());
            covariance.setYz(posError.getYz());
            covariance.setZz(posError.getZz());
            dataMessageTrack.getPositionECEF().setPosCovariance(covariance);
          }
          DataMessage.Track.RFQualities rfQualities = new DataMessage.Track.RFQualities();
          if (track.isApproaching() != null)
          {
            rfQualities.setApproaching(track.isApproaching());
          }
          if (track.getBandwidth() != null)
          {
            rfQualities.setBandwidth(track.getBandwidth());
          }
          if (track.getFrequency() != null)
          {
            rfQualities.setFrequency(track.getFrequency());
          }
          if (track.getGroupId() != null)
          {
            rfQualities.setGroupId(track.getGroupId());
          }
          if (track.getMacAddress() != null)
          {
            rfQualities.setMacAddress(track.getMacAddress());
          }
          if (track.getNoiseStrength() != null)
          {
            rfQualities.setNoiseStrength(track.getNoiseStrength());
          }
          if (track.getSignalConf() != null)
          {
            rfQualities.setSignalConf(track.getSignalConf());
          }
          if (track.getSignalStrength() != null)
          {
            rfQualities.setSignalStrength(track.getSignalStrength());
          }
          if (track.getSignalType() != null)
          {
            rfQualities.setSignalType(track.getSignalType());
          }
          //Temporary hack to override simulator
          if (rfQualities.getSignalType() == "default")
          {
            rfQualities = new DataMessage.Track.RFQualities();
          }
          dataMessageTrack.setRFQualities(rfQualities);
          dataMessage.getTrack().add(dataMessageTrack);
        }
        finalResult.add(dataMessage);
      }
    }
    return finalResult;
  }
}
