

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
 *       &lt;attribute name="xx" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="xy" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="xz" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="yy" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="yz" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="zz" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "PosError")
public class PosError {

    @XmlAttribute(name = "xx", required = true)
    protected double xx;
    @XmlAttribute(name = "xy", required = true)
    protected double xy;
    @XmlAttribute(name = "xz", required = true)
    protected double xz;
    @XmlAttribute(name = "yy", required = true)
    protected double yy;
    @XmlAttribute(name = "yz", required = true)
    protected double yz;
    @XmlAttribute(name = "zz", required = true)
    protected double zz;

    /**
     * Gets the value of the xx property.
     *
     */
    public double getXx() {
        return xx;
    }

    /**
     * Sets the value of the xx property.
     *
     */
    public void setXx(double value) {
        this.xx = value;
    }

    /**
     * Gets the value of the xy property.
     *
     */
    public double getXy() {
        return xy;
    }

    /**
     * Sets the value of the xy property.
     *
     */
    public void setXy(double value) {
        this.xy = value;
    }

    /**
     * Gets the value of the xz property.
     *
     */
    public double getXz() {
        return xz;
    }

    /**
     * Sets the value of the xz property.
     *
     */
    public void setXz(double value) {
        this.xz = value;
    }

    /**
     * Gets the value of the yy property.
     *
     */
    public double getYy() {
        return yy;
    }

    /**
     * Sets the value of the yy property.
     *
     */
    public void setYy(double value) {
        this.yy = value;
    }

    /**
     * Gets the value of the yz property.
     *
     */
    public double getYz() {
        return yz;
    }

    /**
     * Sets the value of the yz property.
     *
     */
    public void setYz(double value) {
        this.yz = value;
    }

    /**
     * Gets the value of the zz property.
     *
     */
    public double getZz() {
        return zz;
    }

    /**
     * Sets the value of the zz property.
     *
     */
    public void setZz(double value) {
        this.zz = value;
    }

}
