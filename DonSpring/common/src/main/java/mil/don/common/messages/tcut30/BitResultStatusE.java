
package mil.don.common.messages.tcut30;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
    name = "bit_result_status_e"
)
@XmlEnum
public enum BitResultStatusE {
  @XmlEnumValue("Not Applicable")
  NOT_APPLICABLE("Not Applicable"),
  @XmlEnumValue("Unknown")
  UNKNOWN("Unknown"),
  @XmlEnumValue("Normal")
  NORMAL("Normal"),
  @XmlEnumValue("Degraded")
  DEGRADED("Degraded"),
  @XmlEnumValue("Faulted")
  FAULTED("Faulted");

  private final String value;

  private BitResultStatusE(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static BitResultStatusE fromValue(String v) {
    BitResultStatusE[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      BitResultStatusE c = var1[var3];
      if (c.value.equals(v)) {
        return c;
      }
    }

    throw new IllegalArgumentException(v);
  }
}
