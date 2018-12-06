
package mil.don.common.messages.tcut21;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for affiliation.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="affiliation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Pending"/>
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="Assumed_Friend"/>
 *     &lt;enumeration value="Friend"/>
 *     &lt;enumeration value="Neutral"/>
 *     &lt;enumeration value="Suspect"/>
 *     &lt;enumeration value="Hostile"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "affiliation")
@XmlEnum
public enum Affiliation {


    /**
     *  Target affiliation determination is pending.
     *
     */
    @XmlEnumValue("Pending")
    PENDING("Pending"),

    /**
     *  Target affiliation is unknown.
     *
     */
    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),

    /**
     *  Target affiliation determination is assumed friendly.
     *
     */
    @XmlEnumValue("Assumed_Friend")
    ASSUMED_FRIEND("Assumed_Friend"),

    /**
     *  Target affiliation has been confirmed as friendly.
     *
     */
    @XmlEnumValue("Friend")
    FRIEND("Friend"),

    /**
     *  Target affiliation is neutral.
     *
     */
    @XmlEnumValue("Neutral")
    NEUTRAL("Neutral"),

    /**
     *  Target affiliation is suspect.
     *
     */
    @XmlEnumValue("Suspect")
    SUSPECT("Suspect"),

    /**
     *  Target affiliation is hostile.
     *
     */
    @XmlEnumValue("Hostile")
    HOSTILE("Hostile");
    private final String value;

    Affiliation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Affiliation fromValue(String v) {
        for (Affiliation c: Affiliation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}