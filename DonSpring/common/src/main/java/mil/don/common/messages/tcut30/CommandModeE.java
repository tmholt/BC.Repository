
package mil.don.common.messages.tcut30;


import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
    name = "command_mode_e"
)
@XmlEnum
public enum CommandModeE {
  @XmlEnumValue("LocalOnly")
  LOCAL_ONLY("LocalOnly"),
  @XmlEnumValue("LocalMaster")
  LOCAL_MASTER("LocalMaster"),
  @XmlEnumValue("RemoteMaster")
  REMOTE_MASTER("RemoteMaster"),
  @XmlEnumValue("RemoteOnly")
  REMOTE_ONLY("RemoteOnly");

  private final String value;

  private CommandModeE(String v) {
    this.value = v;
  }

  public String value() {
    return this.value;
  }

  public static CommandModeE fromValue(String v) {
    CommandModeE[] var1 = values();
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      CommandModeE c = var1[var3];
      if (c.value.equals(v)) {
        return c;
      }
    }

    throw new IllegalArgumentException(v);
  }
}
