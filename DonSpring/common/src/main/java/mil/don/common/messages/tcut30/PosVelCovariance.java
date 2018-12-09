

package mil.don.common.messages.tcut30;



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
    name = "PosVelCovariance"
)
public class PosVelCovariance {
  @XmlAttribute(
      name = "XX",
      required = true
  )
  protected double xx;
  @XmlAttribute(
      name = "XY",
      required = true
  )
  protected double xy;
  @XmlAttribute(
      name = "XZ",
      required = true
  )
  protected double xz;
  @XmlAttribute(
      name = "XXdot",
      required = true
  )
  protected double xXdot;
  @XmlAttribute(
      name = "XYdot",
      required = true
  )
  protected double xYdot;
  @XmlAttribute(
      name = "XZdot",
      required = true
  )
  protected double xZdot;
  @XmlAttribute(
      name = "YY",
      required = true
  )
  protected double yy;
  @XmlAttribute(
      name = "YZ",
      required = true
  )
  protected double yz;
  @XmlAttribute(
      name = "YXdot",
      required = true
  )
  protected double yXdot;
  @XmlAttribute(
      name = "YYdot",
      required = true
  )
  protected double yYdot;
  @XmlAttribute(
      name = "YZdot",
      required = true
  )
  protected double yZdot;
  @XmlAttribute(
      name = "ZZ",
      required = true
  )
  protected double zz;
  @XmlAttribute(
      name = "ZXdot",
      required = true
  )
  protected double zXdot;
  @XmlAttribute(
      name = "ZYdot",
      required = true
  )
  protected double zYdot;
  @XmlAttribute(
      name = "ZZdot",
      required = true
  )
  protected double zZdot;
  @XmlAttribute(
      name = "XdotXdot",
      required = true
  )
  protected double xdotXdot;
  @XmlAttribute(
      name = "XdotYdot",
      required = true
  )
  protected double xdotYdot;
  @XmlAttribute(
      name = "XdotZdot",
      required = true
  )
  protected double xdotZdot;
  @XmlAttribute(
      name = "YdotYdot",
      required = true
  )
  protected double ydotYdot;
  @XmlAttribute(
      name = "YdotZdot",
      required = true
  )
  protected double ydotZdot;
  @XmlAttribute(
      name = "ZdotZdot",
      required = true
  )
  protected double zdotZdot;

  public PosVelCovariance() {
  }

  public double getXX() {
    return this.xx;
  }

  public void setXX(double value) {
    this.xx = value;
  }

  public double getXY() {
    return this.xy;
  }

  public void setXY(double value) {
    this.xy = value;
  }

  public double getXZ() {
    return this.xz;
  }

  public void setXZ(double value) {
    this.xz = value;
  }

  public double getXXdot() {
    return this.xXdot;
  }

  public void setXXdot(double value) {
    this.xXdot = value;
  }

  public double getXYdot() {
    return this.xYdot;
  }

  public void setXYdot(double value) {
    this.xYdot = value;
  }

  public double getXZdot() {
    return this.xZdot;
  }

  public void setXZdot(double value) {
    this.xZdot = value;
  }

  public double getYY() {
    return this.yy;
  }

  public void setYY(double value) {
    this.yy = value;
  }

  public double getYZ() {
    return this.yz;
  }

  public void setYZ(double value) {
    this.yz = value;
  }

  public double getYXdot() {
    return this.yXdot;
  }

  public void setYXdot(double value) {
    this.yXdot = value;
  }

  public double getYYdot() {
    return this.yYdot;
  }

  public void setYYdot(double value) {
    this.yYdot = value;
  }

  public double getYZdot() {
    return this.yZdot;
  }

  public void setYZdot(double value) {
    this.yZdot = value;
  }

  public double getZZ() {
    return this.zz;
  }

  public void setZZ(double value) {
    this.zz = value;
  }

  public double getZXdot() {
    return this.zXdot;
  }

  public void setZXdot(double value) {
    this.zXdot = value;
  }

  public double getZYdot() {
    return this.zYdot;
  }

  public void setZYdot(double value) {
    this.zYdot = value;
  }

  public double getZZdot() {
    return this.zZdot;
  }

  public void setZZdot(double value) {
    this.zZdot = value;
  }

  public double getXdotXdot() {
    return this.xdotXdot;
  }

  public void setXdotXdot(double value) {
    this.xdotXdot = value;
  }

  public double getXdotYdot() {
    return this.xdotYdot;
  }

  public void setXdotYdot(double value) {
    this.xdotYdot = value;
  }

  public double getXdotZdot() {
    return this.xdotZdot;
  }

  public void setXdotZdot(double value) {
    this.xdotZdot = value;
  }

  public double getYdotYdot() {
    return this.ydotYdot;
  }

  public void setYdotYdot(double value) {
    this.ydotYdot = value;
  }

  public double getYdotZdot() {
    return this.ydotZdot;
  }

  public void setYdotZdot(double value) {
    this.ydotZdot = value;
  }

  public double getZdotZdot() {
    return this.zdotZdot;
  }

  public void setZdotZdot(double value) {
    this.zdotZdot = value;
  }
}
