

package mil.don.common.messages.tcut21;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="x" use="required" type="{}xyz_position" />
 *       &lt;attribute name="y" use="required" type="{}xyz_position" />
 *       &lt;attribute name="z" use="required" type="{}xyz_position" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "XYZPos")
public class XYZPos {

    @XmlAttribute(name = "x", required = true)
    protected double x;
    @XmlAttribute(name = "y", required = true)
    protected double y;
    @XmlAttribute(name = "z", required = true)
    protected double z;

    /**
     * Gets the value of the x property.
     *
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     *
     */
    public void setX(double value) {
        this.x = value;
    }

    /**
     * Gets the value of the y property.
     *
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     *
     */
    public void setY(double value) {
        this.y = value;
    }

    /**
     * Gets the value of the z property.
     *
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the value of the z property.
     *
     */
    public void setZ(double value) {
        this.z = value;
    }

}
