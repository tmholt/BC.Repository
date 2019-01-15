

package mil.don.common.messages.tcut30;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"value"}
)
@XmlRootElement(
    name = "Discrimination"
)
public class Discrimination implements Serializable
{
  @XmlValue
  protected String value;
  @XmlAttribute(
      name = "confidence"
  )
  protected Float confidence;
  @XmlAttribute(
      name = "feature"
  )
  protected String feature;

  public Discrimination() {
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Float getConfidence() {
    return this.confidence;
  }

  public void setConfidence(Float value) {
    this.confidence = value;
  }

  public String getFeature() {
    return this.feature;
  }

  public void setFeature(String value) {
    this.feature = value;
  }
}
