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

package mil.don.common.configuration;

import java.util.HashMap;
import java.util.Map;

import mil.don.common.coordinates.Ecef;
import mil.don.common.coordinates.Lla;

public class DeviceConfiguration
{

    public static class DeviceConfigurationPosition
    {
        private final Lla _lla = new Lla();
        private final Ecef _ecef = new Ecef();

        public Lla getLla()
        {
            return _lla;
        }
        public Ecef getEcef()
        {
            return _ecef;
        }
    }

    public static class DeviceConfigurationComms
    {
        private String _commandUri;
        private String _dataUri;

        public String getCommandUri()
        {
            return _commandUri;
        }
        public void setCommandUri(String commandUri)
        {
            this._commandUri = commandUri;
        }
        public String getDataUri()
        {
            return _dataUri;
        }
        public void setDataUri(String dataUri)
        {
            this._dataUri = dataUri;
        }
    }

    private String _name;
    private String _type;
    private String _symbolCode;
    private double _range;
    private final DeviceConfigurationPosition _position = new DeviceConfigurationPosition();
    private final DeviceConfigurationComms _comms = new DeviceConfigurationComms();
    private final Map<String, String> _options = new HashMap<>();

    // region properties

    public String getName()
    {
        return _name;
    }
    public void setName(String name)
    {
        this._name = name;
    }
    public String getType()
    {
        return _type;
    }
    public void setType(String type)
    {
        this._type = type;
    }
    public String getSymbolCode()
    {
        return _symbolCode;
    }
    public void setSymbolCode(String symbolCode)
    {
        this._symbolCode = symbolCode;
    }
    public double getRange()
    {
        return _range;
    }
    public void setRange(double range)
    {
        this._range = range;
    }
    public DeviceConfigurationPosition getPosition()
    {
        return _position;
    }
    public DeviceConfigurationComms getComms()
    {
        return _comms;
    }
    public Map<String, String> getOptions()
    {
        return _options;
    }

    // endregion

}
