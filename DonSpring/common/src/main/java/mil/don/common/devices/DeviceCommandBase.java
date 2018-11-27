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

package mil.don.common.devices;


import java.io.Serializable;
import java.util.Date;

public class DeviceCommandBase implements Serializable
{
    private String _deviceId;
    private String _clientName;
    private String _clientToken;
    private Date _timestamp;

    public String getDeviceId() { return _deviceId; }
    public DeviceCommandBase setDeviceId(String deviceId) { _deviceId = deviceId; return this; }

    public String getClientName() { return _clientName; }
    public DeviceCommandBase setClientName(String clientName) { _clientName = clientName; return this; }

    public String getClientToken() { return _clientToken; }
    public DeviceCommandBase setClientToken(String clientToken) { _clientToken = clientToken; return this; }

    public Date getTimestamp() { return _timestamp; }
    public DeviceCommandBase setDeviceId(Date timestamp) { _timestamp = timestamp; return this; }

    // this is just here for testing. I think we want specific command classes.
    // the question is: where would this live, so that both devices and clients could access it,
    // and it could be added to as more commands and devices are added?
    private String _command;

    public String getCommand() { return _command; }
    public DeviceCommandBase setCommand(String command) { _command = command; return this; }

    @Override
    public String toString() {
        return "Command: '" + _command + "' to '" + _deviceId + "'.";
    }
}
