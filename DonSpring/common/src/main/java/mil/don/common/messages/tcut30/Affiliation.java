
package mil.don.common.messages.tcut30;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
    name = "affiliation"
)
@XmlEnum
public enum Affiliation {
  @XmlEnumValue("Pending")
  PENDING("Pending"),
  @XmlEnumValue("Unknown")
  UNKNOWN("Unknown"),
  @XmlEnumValue("Assumed_Friend")
  ASSUMED_FRIEND("Assumed_Friend"),
  @XmlEnumValue("Friend")
  FRIEND("Friend"),
  @XmlEnumValue("Neutral")
  NEUTRAL("Neutral"),
  @XmlEnumValue("Suspect")
  SUSPECT("Suspect"),
  @XmlEnumValue("Hostile")
  HOSTILE("Hostile");

  private final String value;

  private Affiliation(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static Affiliation fromValue(String v) {
    Affiliation[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      Affiliation c = var1[var3];
      if (c.value.equals(v)) {
        return c;
      }
    }

    throw new IllegalArgumentException(v);
  }
}
