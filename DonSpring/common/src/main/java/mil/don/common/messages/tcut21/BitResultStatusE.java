
package mil.don.common.messages.tcut21;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bit_result_status_e.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="bit_result_status_e">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Not Applicable"/>
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="Normal"/>
 *     &lt;enumeration value="Degraded"/>
 *     &lt;enumeration value="Faulted"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "bit_result_status_e")
@XmlEnum
public enum BitResultStatusE {


    /**
     * This system's BIT status is not applicable.
     *
     */
    @XmlEnumValue("Not Applicable")
    NOT_APPLICABLE("Not Applicable"),

    /**
     * This system's BIT status is unknown.
     *
     */
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),

    /**
     * This system's BIT status is normal.
     *
     */
    @XmlEnumValue("Normal")
    NORMAL("Normal"),

    /**
     * This system's BIT status is degraded.
     *
     */
    @XmlEnumValue("Degraded")
    DEGRADED("Degraded"),

    /**
     * This system is currently faulted.
     *
     */
    @XmlEnumValue("Faulted")
    FAULTED("Faulted");
    private final String value;

    BitResultStatusE(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BitResultStatusE fromValue(String v) {
        for (BitResultStatusE c: BitResultStatusE.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
