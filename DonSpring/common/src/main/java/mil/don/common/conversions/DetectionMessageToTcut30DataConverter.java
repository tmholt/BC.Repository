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

import mil.don.common.devices.DetectionMessage;
import mil.don.common.messages.tcut30.Classifications;
import mil.don.common.messages.tcut30.DataMessage;
import mil.don.common.messages.tcut30.LineOfBearing;
import mil.don.common.messages.tcut30.OverallClassTypeE;
import mil.don.common.messages.tcut30.SystemTypeE;

public class DetectionMessageToTcut30DataConverter
{
  public DataMessage convert(DetectionMessage message)
  {
    DataMessage result = new DataMessage();
    result.setRevision("3.0");
    result.setSourceSystem("Defaulted value from converter");

    result.setSourceType(SystemTypeE.ACOUSTIC);

    // set timestamp
    long ts = message.getTimestamp().getTime();
    result.setTime(BigInteger.valueOf(ts));
    result.setTimeIsValid(ts > 0);

    // add track
    DataMessage.Track track = new DataMessage.Track();
    track.setUpdateTime(BigInteger.valueOf(ts));
    track.setClassifications(new Classifications());
    track.getClassifications().setOverallType(OverallClassTypeE.UAS);
    track.setEnd(false);
    track.setTrackId(message.getTrackId());

    LineOfBearing lob = new LineOfBearing();// track.getLineOfBearing();
    lob.setAzimuth(discoData.getAzimuth().floatValue());
    //lob.setAzimuthErr(DEFAULT_AZIMUTH_ERROR);
    lob.setElevation(discoData.getElevation().floatValue());
    //lob.setElevationErr(DEFAULT_ELEVATION_ERROR);
    track.setLineOfBearing(lob);

    // add track to data message
    result.getTrack().add(track);
    result.setMsgCount(1); //??

    return result;
  }
}
