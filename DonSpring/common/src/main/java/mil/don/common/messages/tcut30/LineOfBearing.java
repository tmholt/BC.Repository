
package mil.don.common.messages.tcut30;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = ""
)
@XmlRootElement(
    name = "LineOfBearing"
)
public class LineOfBearing implements Serializable
{
  @XmlAttribute(
      name = "_azimuth",
      required = true
  )
  protected float azimuth;
  @XmlAttribute(
      name = "_elevation"
  )
  protected Float elevation;
  @XmlAttribute(
      name = "azimuth_err"
  )
  protected Float azimuthErr;
  @XmlAttribute(
      name = "elevation_err"
  )
  protected Float elevationErr;
  @XmlAttribute(
      name = "min_range"
  )
  protected Float minRange;
  @XmlAttribute(
      name = "max_range"
  )
  protected Float maxRange;

  public LineOfBearing() {
  }

  public float getAzimuth() {
    return this.azimuth;
  }

  public void setAzimuth(float value) {
    this.azimuth = value;
  }

  public Float getElevation() {
    return this.elevation;
  }

  public void setElevation(Float value) {
    this.elevation = value;
  }

  public Float getAzimuthErr() {
    return this.azimuthErr;
  }

  public void setAzimuthErr(Float value) {
    this.azimuthErr = value;
  }

  public Float getElevationErr() {
    return this.elevationErr;
  }

  public void setElevationErr(Float value) {
    this.elevationErr = value;
  }

  public Float getMinRange() {
    return this.minRange;
  }

  public void setMinRange(Float value) {
    this.minRange = value;
  }

  public Float getMaxRange() {
    return this.maxRange;
  }

  public void setMaxRange(Float value) {
    this.maxRange = value;
  }
}
