
package mil.don.common.messages.tcut30;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
    name = "overall_class_type_e"
)
@XmlEnum
public enum OverallClassTypeE {
  @XmlEnumValue("Aircraft")
  AIRCRAFT("Aircraft"),
  @XmlEnumValue("Bird")
  BIRD("Bird"),
  @XmlEnumValue("GroundStation")
  GROUND_STATION("GroundStation"),
  @XmlEnumValue("GroundVehicle")
  GROUND_VEHICLE("GroundVehicle"),
  @XmlEnumValue("Pending")
  PENDING("Pending"),
  @XmlEnumValue("Projectile")
  PROJECTILE("Projectile"),
  UAS("UAS"),
  @XmlEnumValue("Unknown")
  UNKNOWN("Unknown");

  private final String value;

  private OverallClassTypeE(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static OverallClassTypeE fromValue(String v) {
    OverallClassTypeE[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      OverallClassTypeE c = var1[var3];
      if (c.value.equals(v)) {
        return c;
      }
    }

    throw new IllegalArgumentException(v);
  }
}
