

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
    name = "PosError"
)
public class PosError implements Serializable
{
  @XmlAttribute(
      name = "xx",
      required = true
  )
  protected double xx;
  @XmlAttribute(
      name = "xy",
      required = true
  )
  protected double xy;
  @XmlAttribute(
      name = "xz",
      required = true
  )
  protected double xz;
  @XmlAttribute(
      name = "yy",
      required = true
  )
  protected double yy;
  @XmlAttribute(
      name = "yz",
      required = true
  )
  protected double yz;
  @XmlAttribute(
      name = "zz",
      required = true
  )
  protected double zz;

  public PosError() {
  }

  public double getXx() {
    return this.xx;
  }

  public void setXx(double value) {
    this.xx = value;
  }

  public double getXy() {
    return this.xy;
  }

  public void setXy(double value) {
    this.xy = value;
  }

  public double getXz() {
    return this.xz;
  }

  public void setXz(double value) {
    this.xz = value;
  }

  public double getYy() {
    return this.yy;
  }

  public void setYy(double value) {
    this.yy = value;
  }

  public double getYz() {
    return this.yz;
  }

  public void setYz(double value) {
    this.yz = value;
  }

  public double getZz() {
    return this.zz;
  }

  public void setZz(double value) {
    this.zz = value;
  }
}
