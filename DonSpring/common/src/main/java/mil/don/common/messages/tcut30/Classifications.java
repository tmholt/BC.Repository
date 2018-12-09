

package mil.don.common.messages.tcut30;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"discrimination"}
)
@XmlRootElement(
    name = "Classifications"
)
public class Classifications {
  @XmlElement(
      name = "Discrimination"
  )
  protected List<Discrimination> discrimination;
  @XmlAttribute(
      name = "man_made"
  )
  protected Boolean manMade;
  @XmlAttribute(
      name = "overall_type"
  )
  protected OverallClassTypeE overallType;
  @XmlAttribute(
      name = "overall_conf"
  )
  protected Float overallConf;

  public Classifications() {
  }

  public List<Discrimination> getDiscrimination() {
    if (this.discrimination == null) {
      this.discrimination = new ArrayList();
    }

    return this.discrimination;
  }

  public Boolean isManMade() {
    return this.manMade;
  }

  public void setManMade(Boolean value) {
    this.manMade = value;
  }

  public OverallClassTypeE getOverallType() {
    return this.overallType;
  }

  public void setOverallType(OverallClassTypeE value) {
    this.overallType = value;
  }

  public Float getOverallConf() {
    return this.overallConf;
  }

  public void setOverallConf(Float value) {
    this.overallConf = value;
  }
}
