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

import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

// support for setting one or more representations of a geo-point
public class CompositeCoordinate implements Serializable
{
    /*
     * ECEF (Earth Centered Earth Fixed) to lat-lon-alt conversions are numerically unstable at the
     * North and South poles, so switch to different calculation when the magnitude of latitude
     * exceeds CLOSE_TO_POLE (89.9 degrees in radians)
     */
    public static final double CLOSE_TO_POLE = Math.toRadians(89.9);

    /* WGS84 Earth approximation of the equatorial radius */
    public static final double EQUATORIAL_RADIUS_M = 6378137.0;    // from WGS 84

    /* WGS84 Earth eccentricity squared */
    public static final double EARTH_ECCENTRICITY_SQUARED = 0.0066943800042608;// from WGS 84


    private EcefCoordinate _ecef;
    private LlaCoordinate _lla;
    private MgrsCoordinate _mgrs;

    // region properties

    public EcefCoordinate getEcef()
    {
        return _ecef;
    }
    public void setEcef(EcefCoordinate ecef)
    {
        this._ecef = ecef;
    }
    public EcefCoordinate setEcef(double x, double y, double z) {
        _ecef = new EcefCoordinate(x, y, z);
        return _ecef;
    }

    public LlaCoordinate getLla()
    {
        return _lla;
    }
    public void setLla(LlaCoordinate lla)
    {
        this._lla = lla;
    }

    public MgrsCoordinate getMgrs()
    {
        return _mgrs;
    }
    public void setMgrs(MgrsCoordinate mgrs)
    {
        this._mgrs = mgrs;
    }

    // endregion

    public LlaCoordinate setLlaHae(double lat, double lon, double alt)
    {
        _lla = new LlaCoordinate()
            .setLatitude(lat)
            .setLongitude(lon)
            .setAltitude(alt)
            .setAltitudeType(AltitudeType.HAE);

        return _lla;
    }

    public LlaCoordinate setLlaHaeFromEcef(EcefCoordinate ecef) {
        _lla = convert(ecef);
        return _lla;
    }

    public void setLlaMsl(double lat, double lon, double alt) {
        _lla = new LlaCoordinate()
            .setLatitude(lat)
            .setLongitude(lon)
            .setAltitude(alt)
            .setAltitudeType(AltitudeType.MSL);
    }

    private static LlaCoordinate convert(EcefCoordinate ecef) {

        double x = ecef.getX();
        double y = ecef.getY();
        double z = ecef.getZ();

        double lon_r = Math.atan2(y, x);
        double p = Math.sqrt(x * x + y * y);
        double geodeticLatNowEst_r = Math.atan2(z, p);
        double e2 = EARTH_ECCENTRICITY_SQUARED;
        double h = 0.0D;

        for ( long iter = 1L; iter <= 9L; ++iter )
        {
            double rnNow = getPvroc(geodeticLatNowEst_r);
            if ( Math.abs(geodeticLatNowEst_r) <= CLOSE_TO_POLE )
            {
                h = p / Math.cos(geodeticLatNowEst_r) - rnNow;
            }
            else
            {
                double sinLat = Math.sin(geodeticLatNowEst_r);
                double l = z + e2 * rnNow * sinLat;
                h = l / sinLat - rnNow;
            }

            double denom = 1.0D - e2 * (rnNow / (rnNow + h));
            geodeticLatNowEst_r = Math.atan(z / (p * denom));
        }

        return new LlaCoordinate(Math.toDegrees(geodeticLatNowEst_r),
            Math.toDegrees(lon_r), h, AltitudeType.HAE);

    }

    /**
     * Get the prime vertical radius of curvature.
     * This is the apparent radius of the Earth based on a local surface normal.  It is the WGS 84
     * equatorial radius at the equator and the WGS 84 polar radius at the poles. In between this
     * radius can be larger than the equatorial radius (which is larger than the polar radius)
     * NOTE: the latitude is in radians
     *
     * @param latRadians the geodetic latitude in radians
     *
     * @return the prime vertical radius of curvature
     */
    private static double getPvroc(double latRadians)
    {
        double sinLat = sin(latRadians);
        return EQUATORIAL_RADIUS_M / sqrt(1.0 - EARTH_ECCENTRICITY_SQUARED * sinLat * sinLat);
    }
}
