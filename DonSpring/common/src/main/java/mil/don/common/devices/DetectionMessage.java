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
import java.util.HashMap;
import java.util.Map;



// an event from a device in the system, reporting a detection has occurred
public class DetectionMessage implements Serializable
{

    private String _detectionType;
    private String _id;
    private Date _timestamp;
    private String _sourceDeviceName;
    private String _sourceDeviceType;

    private final Map<String, String> _properties = new HashMap<>();





    public String getDetectionType()
    {
        return _detectionType;
    }

    public DetectionMessage setDetectionType(String detectionType)
    {
        this._detectionType = detectionType;
        return this;
    }

    public String getId()
    {
        return _id;
    }

    public DetectionMessage setId(String id)
    {
        this._id = id;
        return this;
    }

    public Date getTimestamp()
    {
        return _timestamp;
    }
    public DetectionMessage setTimestamp(Date timestamp) { this._timestamp = timestamp; return this; }

    public String getSourceName() {
        return _sourceDeviceName;
    }
    public DetectionMessage setSourceName(String sourceName) { this._sourceDeviceName = sourceName; return this; }

    public String getSourceDeviceType() {
        return _sourceDeviceType;
    }
    public DetectionMessage setSourceDeviceType(String deviceType) { this._sourceDeviceType = deviceType; return this; }


    public Map<String, String> getProperties()
    {
        return _properties;
    }

    @Override
    public String toString() {
        return String.format("device.detection last='%tc', id='%s', type='%s', source='%s', source_type='%s'",
            _timestamp, _id, _detectionType, _sourceDeviceName, _sourceDeviceType);
    }
}