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

public class EcefCoordinate implements Serializable
{
    private double _x;
    private double _y;
    private double _z;

    public EcefCoordinate()
    {
    }

    public EcefCoordinate(double x, double y, double z) {
        _x = x;
        _y = y;
        _z = z;
    }

    public double getX()
    {
        return _x;
    }
    public EcefCoordinate setX(double x) { this._x = x; return this; }

    public double getY()
    {
        return _y;
    }
    public EcefCoordinate setY(double y) { this._y = y; return this; }

    public double getZ()
    {
        return _z;
    }
    public EcefCoordinate setZ(double z) { this._z = z; return this; }
}
