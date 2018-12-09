package mil.don.common.messages.tcut30;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
    name = "system_type_e"
)
@XmlEnum
public enum SystemTypeE {
  @XmlEnumValue("Other")
  OTHER("Other"),
  @XmlEnumValue("Acoustic")
  ACOUSTIC("Acoustic"),
  @XmlEnumValue("Radar")
  RADAR("Radar"),
  EW("EW"),
  @XmlEnumValue("Camera")
  CAMERA("Camera"),
  @XmlEnumValue("Threat")
  THREAT("Threat");

  private final String value;

  private SystemTypeE(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static SystemTypeE fromValue(String v) {
    SystemTypeE[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      SystemTypeE c = var1[var3];
      if (c.value.equals(v)) {
        return c;
      }
    }

    throw new IllegalArgumentException(v);
  }
}
