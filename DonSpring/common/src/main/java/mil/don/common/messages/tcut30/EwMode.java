
package mil.don.common.messages.tcut30;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
    name = "ew_mode"
)
@XmlEnum
public enum EwMode {
  @XmlEnumValue("standby")
  STANDBY("standby"),
  @XmlEnumValue("run")
  RUN("run"),
  @XmlEnumValue("halt")
  HALT("halt"),
  @XmlEnumValue("config_changeover")
  CONFIG_CHANGEOVER("config_changeover"),
  @XmlEnumValue("offline_bit")
  OFFLINE_BIT("offline_bit"),
  @XmlEnumValue("zeroize")
  ZEROIZE("zeroize");

  private final String value;

  private EwMode(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static EwMode fromValue(String v) {
    EwMode[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      EwMode c = var1[var3];
      if (c.value.equals(v)) {
        return c;
      }
    }

    throw new IllegalArgumentException(v);
  }
}
