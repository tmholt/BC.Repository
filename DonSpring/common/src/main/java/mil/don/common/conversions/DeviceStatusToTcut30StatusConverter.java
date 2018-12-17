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

import mil.don.common.messages.tcut30.BitResultStatusE;
import mil.don.common.messages.tcut30.EwMode;
import mil.don.common.messages.tcut30.StatusMessage;
import mil.don.common.messages.tcut30.SystemTypeE;
import mil.don.common.status.DeviceStatusMessage;

public class DeviceStatusToTcut30StatusConverter
{

  private static final String SYSTEM_SW_VERSION = "1.0.0.0";
  private static final String SYSTEM_MISSION_NAME = "MEDUSA"; // required for TCUT3 status. no idea


  public StatusMessage convert(DeviceStatusMessage message) {

    if ( message == null ) return null;

    StatusMessage result = new StatusMessage();
    result.setRevision("3.0");
    result.setSourceSystem(message.getSourceName());
    result.setSourceType(SystemTypeE.EW);

    result.setTime(BigInteger.valueOf(System.currentTimeMillis()));
    result.setTimeIsValid(false);

    // message count. expected to be 0 for status
    result.setMsgCount(0);

    // set up the internal StatusMessage subclass
    StatusMessage.Status internalStatus = new StatusMessage.Status();
    internalStatus.setSwVersion(SYSTEM_SW_VERSION);
    internalStatus.setOverallStatus(BitResultStatusE.NORMAL);
    internalStatus.setMission(SYSTEM_MISSION_NAME); // NOTE: REQUIRED
    internalStatus.setSystemState(EwMode.HALT); // taken from 21 conversion. why HALT??
    // no spacial factors
    // no optical factors
    // no mission
    // no coordination factors
    // no rf factors
    // no hardware version
    result.setStatus(internalStatus);
    return result;
  }
}
