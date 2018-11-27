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

package mil.don.common.coordinates;

import java.io.Serializable;

public class LlaCoordinate implements Serializable
{
    private double _latitude;
    private double _longitude;
    private double _altitude;
    private AltitudeType _altitudeType;

    public LlaCoordinate() {

    }

    public LlaCoordinate(double lat, double lon, double alt, AltitudeType altType) {
        _latitude = lat;
        _longitude = lon;
        _altitude = alt;
        _altitudeType = altType;
    }

    public double getLatitude()
    {
        return _latitude;
    }
    public LlaCoordinate setLatitude(double latitude)
    {
        this._latitude = latitude; return this;
    }
    public double getLongitude()
    {
        return _longitude;
    }
    public LlaCoordinate setLongitude(double longitude)
    {
        this._longitude = longitude; return this;
    }
    public double getAltitude()
    {
        return _altitude;
    }
    public LlaCoordinate setAltitude(double altitude)
    {
        this._altitude = altitude; return this;
    }
    public AltitudeType getAltitudeType()
    {
        return _altitudeType;
    }
    public LlaCoordinate setAltitudeType(AltitudeType altType)
    {
        this._altitudeType = altType;
        return this;
    }
}
