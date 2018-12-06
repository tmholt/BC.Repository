

package mil.don.common.messages.tcut21;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ew_mode.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ew_mode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="standby"/>
 *     &lt;enumeration value="run"/>
 *     &lt;enumeration value="halt"/>
 *     &lt;enumeration value="config_changeover"/>
 *     &lt;enumeration value="offline_bit"/>
 *     &lt;enumeration value="zeroize"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
@XmlType(name = "ew_mode")
@XmlEnum
public enum EwMode {


    /**
     * The digital portion of the system will continue to run but the high power amplifiers will be disabled. This is the preferred mode to leave the system in for extended periods of inactivity, as receivers will still run.
     *
     */
    @XmlEnumValue("standby")
    STANDBY("standby"),

    /**
     * All functionality is running and any jamming that has been requested by will begin. Power amplifiers may be activated in this mode.
     *
     */
    @XmlEnumValue("run")
    RUN("run"),

    /**
     * The system is halted.  No receive or transmit schedulers are running.
     *
     */
    @XmlEnumValue("halt")
    HALT("halt"),

    /**
     * Indicates the system is currently changing a config. The system will be halted temporarily, and a new config is read out of non-volatile memory. Upon successful loading of a config, the system will continue transitioning into run or standby automatically.
     *
     */
    @XmlEnumValue("config_changeover")
    CONFIG_CHANGEOVER("config_changeover"),

    /**
     * Indicates the system is currently down running built in tests. Upon completion, the system will move to standby.
     *
     */
    @XmlEnumValue("offline_bit")
    OFFLINE_BIT("offline_bit"),

    /**
     * The system is currently erasing all sensitive information from the system, rendering it inoperable. It will need to be restored by external tools prior to being used again.
     *
     */
    @XmlEnumValue("zeroize")
    ZEROIZE("zeroize");
    private final String value;

    EwMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EwMode fromValue(String v) {
        for (EwMode c: EwMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}