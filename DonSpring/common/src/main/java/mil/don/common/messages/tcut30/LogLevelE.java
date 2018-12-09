
package mil.don.common.messages.tcut30;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
    name = "log_level_e"
)
@XmlEnum
public enum LogLevelE {
  @XmlEnumValue("Critical")
  CRITICAL("Critical"),
  @XmlEnumValue("Warning")
  WARNING("Warning"),
  @XmlEnumValue("Info")
  INFO("Info"),
  @XmlEnumValue("Debug")
  DEBUG("Debug");

  private final String value;

  private LogLevelE(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static LogLevelE fromValue(String v) {
    LogLevelE[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      LogLevelE c = var1[var3];
      if (c.value.equals(v)) {
        return c;
      }
    }

    throw new IllegalArgumentException(v);
  }
}
