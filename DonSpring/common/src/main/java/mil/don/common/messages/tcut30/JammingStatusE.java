
package mil.don.common.messages.tcut30;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
    name = "jamming_status_e"
)
@XmlEnum
public enum JammingStatusE {
  @XmlEnumValue("Unknown")
  UNKNOWN("Unknown"),
  @XmlEnumValue("Normal")
  NORMAL("Normal"),
  @XmlEnumValue("Degraded")
  DEGRADED("Degraded"),
  @XmlEnumValue("Jammed")
  JAMMED("Jammed");

  private final String value;

  private JammingStatusE(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static JammingStatusE fromValue(String v) {
    JammingStatusE[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      JammingStatusE c = var1[var3];
      if (c.value.equals(v)) {
        return c;
      }
    }

    throw new IllegalArgumentException(v);
  }
}
