

package mil.don.common.messages.tcut30;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
    name = "ecm_mode"
)
@XmlEnum
public enum EcmMode {
  @XmlEnumValue("disable")
  DISABLE("disable"),
  @XmlEnumValue("initializing")
  INITIALIZING("initializing"),
  @XmlEnumValue("running")
  RUNNING("running");

  private final String value;

  private EcmMode(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static EcmMode fromValue(String v) {
    EcmMode[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      EcmMode c = var1[var3];
      if (c.value.equals(v)) {
        return c;
      }
    }

    throw new IllegalArgumentException(v);
  }
}
