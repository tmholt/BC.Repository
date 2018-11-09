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

// support for setting one or more representations of a geo-point
public class CompositeCoordinate
{
    private Ecef _ecef;
    private Lla _lla;
    private Mgrs _mgrs;

    // region properties

    public Ecef getEcef()
    {
        return _ecef;
    }

    public void setEcef(Ecef ecef)
    {
        this._ecef = ecef;
    }

    public Lla getLla()
    {
        return _lla;
    }
    public Mgrs getMgrs()
    {
        return _mgrs;
    }
    public void setMgrs(Mgrs mgrs)
    {
        this._mgrs = mgrs;
    }

    // endregion

    public void setLlaHae(double lat, double lon, double alt)
    {
        _lla = new Lla()
            .setLatitude(lat)
            .setLongitude(lon)
            .setAltitude(alt)
            .setAltType(AltitudeType.HAE);
    }

    public void setLlaMsl(double lat, double lon, double alt) {
        _lla = new Lla()
            .setLatitude(lat)
            .setLongitude(lon)
            .setAltitude(alt)
            .setAltType(AltitudeType.MSL);
    }
}
