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

import java.util.ArrayList;
import java.util.List;

import mil.don.common.coordinates.Lla;

public class ProtectedAreaConfiguration
{
    public enum ShapeType {
        POLYGON,
        RECTANGLE,
        CIRCLE
    }

    private String _name;
    private ShapeType _shape;
    private int _warnTime;
    private boolean _displayWarningArea;
    private final List<Lla> _coordinates = new ArrayList<>();

    public String getName()
    {
        return _name;
    }
    public void setName(String name)
    {
        this._name = name;
    }
    public ShapeType getShape()
    {
        return _shape;
    }
    public void setShape(ShapeType shape)
    {
        this._shape = shape;
    }
    public int getWarnTime()
    {
        return _warnTime;
    }
    public void setWarnTime(int warnTime)
    {
        this._warnTime = warnTime;
    }
    public boolean getDisplayWarningArea()
    {
        return _displayWarningArea;
    }
    public void setDisplayWarningArea(boolean displayWarningArea) { this._displayWarningArea = displayWarningArea; }
    public List<Lla> getCoordinates()
    {
        return _coordinates;
    }

}
