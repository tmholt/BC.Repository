
package mil.don.common.messages.tcut30;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
    name = "camera_type"
)
@XmlEnum
public enum CameraType {
  DLTV("DLTV"),
  IR("IR"),
  @XmlEnumValue("ShortWaveIR")
  SHORT_WAVE_IR("ShortWaveIR"),
  @XmlEnumValue("Other")
  OTHER("Other");

  private final String value;

  private CameraType(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static CameraType fromValue(String v) {
    CameraType[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      CameraType c = var1[var3];
      if (c.value.equals(v)) {
        return c;
      }
    }

    throw new IllegalArgumentException(v);
  }
}
