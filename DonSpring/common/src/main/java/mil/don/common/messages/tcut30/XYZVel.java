

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
    name = "XYZVel"
)
public class XYZVel {
  @XmlAttribute(
      name = "x",
      required = true
  )
  protected double x;
  @XmlAttribute(
      name = "y",
      required = true
  )
  protected double y;
  @XmlAttribute(
      name = "z",
      required = true
  )
  protected double z;

  public XYZVel() {
  }

  public double getX() {
    return this.x;
  }

  public void setX(double value) {
    this.x = value;
  }

  public double getY() {
    return this.y;
  }

  public void setY(double value) {
    this.y = value;
  }

  public double getZ() {
    return this.z;
  }

  public void setZ(double value) {
    this.z = value;
  }
}
