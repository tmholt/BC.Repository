

package mil.don.common.messages.tcut30;


import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"track", "threat"}
)
@XmlRootElement(
    name = "DataMessage"
)
public class DataMessage implements Serializable
{
  @XmlElement(
      name = "Track"
  )
  protected List<DataMessage.Track> track;
  @XmlElement(
      name = "Threat"
  )
  protected List<DataMessage.Threat> threat;
  @XmlAttribute(
      name = "revision",
      required = true
  )
  protected String revision;
  @XmlAttribute(
      name = "source_system",
      required = true
  )
  protected String sourceSystem;
  @XmlAttribute(
      name = "source_type",
      required = true
  )
  protected SystemTypeE sourceType;
  @XmlAttribute(
      name = "time",
      required = true
  )
  protected BigInteger time;
  @XmlAttribute(
      name = "time_is_valid",
      required = true
  )
  protected boolean timeIsValid;
  @XmlAttribute(
      name = "msg_count",
      required = true
  )
  @XmlSchemaType(
      name = "unsignedShort"
  )
  protected int msgCount;

  public DataMessage() {
  }

  public List<DataMessage.Track> getTrack() {
    if (this.track == null) {
      this.track = new ArrayList();
    }

    return this.track;
  }

  public List<DataMessage.Threat> getThreat() {
    if (this.threat == null) {
      this.threat = new ArrayList();
    }

    return this.threat;
  }

  public String getRevision() {
    return this.revision;
  }

  public void setRevision(String value) {
    this.revision = value;
  }

  public String getSourceSystem() {
    return this.sourceSystem;
  }

  public void setSourceSystem(String value) {
    this.sourceSystem = value;
  }

  public SystemTypeE getSourceType() {
    return this.sourceType;
  }

  public void setSourceType(SystemTypeE value) {
    this.sourceType = value;
  }

  public BigInteger getTime() {
    return this.time;
  }

  public void setTime(BigInteger value) {
    this.time = value;
  }

  public boolean isTimeIsValid() {
    return this.timeIsValid;
  }

  public void setTimeIsValid(boolean value) {
    this.timeIsValid = value;
  }

  public int getMsgCount() {
    return this.msgCount;
  }

  public void setMsgCount(int value) {
    this.msgCount = value;
  }

  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(
      name = "",
      propOrder = {"lineOfBearing", "positionECEF", "ballisticSolution", "classifications", "acousticQualities", "rfQualities"}
  )
  public static class Track implements Serializable {
    @XmlElement(
        name = "LineOfBearing"
    )
    protected LineOfBearing lineOfBearing;
    @XmlElement(
        name = "PositionECEF"
    )
    protected DataMessage.Track.PositionECEF positionECEF;
    @XmlElement(
        name = "BallisticSolution"
    )
    protected DataMessage.Track.BallisticSolution ballisticSolution;
    @XmlElement(
        name = "Classifications"
    )
    protected Classifications classifications;
    @XmlElement(
        name = "AcousticQualities"
    )
    protected DataMessage.Track.AcousticQualities acousticQualities;
    @XmlElement(
        name = "RFQualities"
    )
    protected DataMessage.Track.RFQualities rfQualities;
    @XmlAttribute(
        name = "track_id",
        required = true
    )
    @XmlSchemaType(
        name = "unsignedInt"
    )
    protected long trackId;
    @XmlAttribute(
        name = "update_time",
        required = true
    )
    protected BigInteger updateTime;
    @XmlAttribute(
        name = "beginning"
    )
    protected Boolean beginning;
    @XmlAttribute(
        name = "end"
    )
    protected Boolean end;
    @XmlAttribute(
        name = "affiliation"
    )
    protected Affiliation affiliation;

    public Track() {
    }

    public LineOfBearing getLineOfBearing() {
      return this.lineOfBearing;
    }

    public void setLineOfBearing(LineOfBearing value) {
      this.lineOfBearing = value;
    }

    public DataMessage.Track.PositionECEF getPositionECEF() {
      return this.positionECEF;
    }

    public void setPositionECEF(DataMessage.Track.PositionECEF value) {
      this.positionECEF = value;
    }

    public DataMessage.Track.BallisticSolution getBallisticSolution() {
      return this.ballisticSolution;
    }

    public void setBallisticSolution(DataMessage.Track.BallisticSolution value) {
      this.ballisticSolution = value;
    }

    public Classifications getClassifications() {
      return this.classifications;
    }

    public void setClassifications(Classifications value) {
      this.classifications = value;
    }

    public DataMessage.Track.AcousticQualities getAcousticQualities() {
      return this.acousticQualities;
    }

    public void setAcousticQualities(DataMessage.Track.AcousticQualities value) {
      this.acousticQualities = value;
    }

    public DataMessage.Track.RFQualities getRFQualities() {
      return this.rfQualities;
    }

    public void setRFQualities(DataMessage.Track.RFQualities value) {
      this.rfQualities = value;
    }

    public long getTrackId() {
      return this.trackId;
    }

    public void setTrackId(long value) {
      this.trackId = value;
    }

    public BigInteger getUpdateTime() {
      return this.updateTime;
    }

    public void setUpdateTime(BigInteger value) {
      this.updateTime = value;
    }

    public Boolean isBeginning() {
      return this.beginning;
    }

    public void setBeginning(Boolean value) {
      this.beginning = value;
    }

    public Boolean isEnd() {
      return this.end;
    }

    public void setEnd(Boolean value) {
      this.end = value;
    }

    public Affiliation getAffiliation() {
      return this.affiliation;
    }

    public void setAffiliation(Affiliation value) {
      this.affiliation = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = ""
    )
    public static class RFQualities {
      @XmlAttribute(
          name = "signal_type",
          required = true
      )
      protected String signalType;
      @XmlAttribute(
          name = "approaching"
      )
      protected Boolean approaching;
      @XmlAttribute(
          name = "bandwidth"
      )
      protected Double bandwidth;
      @XmlAttribute(
          name = "channel_count"
      )
      @XmlSchemaType(
          name = "unsignedShort"
      )
      protected Integer channelCount;
      @XmlAttribute(
          name = "density"
      )
      protected Float density;
      @XmlAttribute(
          name = "detection_count"
      )
      @XmlSchemaType(
          name = "unsignedShort"
      )
      protected Integer detectionCount;
      @XmlAttribute(
          name = "frequency"
      )
      protected Double frequency;
      @XmlAttribute(
          name = "group_id"
      )
      @XmlSchemaType(
          name = "unsignedShort"
      )
      protected Integer groupId;
      @XmlAttribute(
          name = "mac_address"
      )
      protected String macAddress;
      @XmlAttribute(
          name = "noise_strength"
      )
      protected Float noiseStrength;
      @XmlAttribute(
          name = "pulse_width"
      )
      protected Float pulseWidth;
      @XmlAttribute(
          name = "pri"
      )
      protected Float pri;
      @XmlAttribute(
          name = "signal_conf"
      )
      protected Float signalConf;
      @XmlAttribute(
          name = "signal_strength"
      )
      protected Float signalStrength;
      @XmlAttribute(
          name = "target_video_stream"
      )
      protected String targetVideoStream;

      public RFQualities() {
      }

      public String getSignalType() {
        return this.signalType;
      }

      public void setSignalType(String value) {
        this.signalType = value;
      }

      public Boolean isApproaching() {
        return this.approaching;
      }

      public void setApproaching(Boolean value) {
        this.approaching = value;
      }

      public Double getBandwidth() {
        return this.bandwidth;
      }

      public void setBandwidth(Double value) {
        this.bandwidth = value;
      }

      public Integer getChannelCount() {
        return this.channelCount;
      }

      public void setChannelCount(Integer value) {
        this.channelCount = value;
      }

      public Float getDensity() {
        return this.density;
      }

      public void setDensity(Float value) {
        this.density = value;
      }

      public Integer getDetectionCount() {
        return this.detectionCount;
      }

      public void setDetectionCount(Integer value) {
        this.detectionCount = value;
      }

      public Double getFrequency() {
        return this.frequency;
      }

      public void setFrequency(Double value) {
        this.frequency = value;
      }

      public Integer getGroupId() {
        return this.groupId;
      }

      public void setGroupId(Integer value) {
        this.groupId = value;
      }

      public String getMacAddress() {
        return this.macAddress;
      }

      public void setMacAddress(String value) {
        this.macAddress = value;
      }

      public Float getNoiseStrength() {
        return this.noiseStrength;
      }

      public void setNoiseStrength(Float value) {
        this.noiseStrength = value;
      }

      public Float getPulseWidth() {
        return this.pulseWidth;
      }

      public void setPulseWidth(Float value) {
        this.pulseWidth = value;
      }

      public Float getPri() {
        return this.pri;
      }

      public void setPri(Float value) {
        this.pri = value;
      }

      public Float getSignalConf() {
        return this.signalConf;
      }

      public void setSignalConf(Float value) {
        this.signalConf = value;
      }

      public Float getSignalStrength() {
        return this.signalStrength;
      }

      public void setSignalStrength(Float value) {
        this.signalStrength = value;
      }

      public String getTargetVideoStream() {
        return this.targetVideoStream;
      }

      public void setTargetVideoStream(String value) {
        this.targetVideoStream = value;
      }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = "",
        propOrder = {"xyzPos", "xyzVel", "posCovariance", "posVelCovariance"}
    )
    public static class PositionECEF implements Serializable {
      @XmlElement(
          name = "XYZPos",
          required = true
      )
      protected XYZPos xyzPos;
      @XmlElement(
          name = "XYZVel"
      )
      protected XYZVel xyzVel;
      @XmlElement(
          name = "PosCovariance"
      )
      protected PosCovariance posCovariance;
      @XmlElement(
          name = "PosVelCovariance"
      )
      protected PosVelCovariance posVelCovariance;

      public PositionECEF() {
      }

      public XYZPos getXYZPos() {
        return this.xyzPos;
      }

      public void setXYZPos(XYZPos value) {
        this.xyzPos = value;
      }

      public XYZVel getXYZVel() {
        return this.xyzVel;
      }

      public void setXYZVel(XYZVel value) {
        this.xyzVel = value;
      }

      public PosCovariance getPosCovariance() {
        return this.posCovariance;
      }

      public void setPosCovariance(PosCovariance value) {
        this.posCovariance = value;
      }

      public PosVelCovariance getPosVelCovariance() {
        return this.posVelCovariance;
      }

      public void setPosVelCovariance(PosVelCovariance value) {
        this.posVelCovariance = value;
      }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = "",
        propOrder = {"poi", "poo"}
    )
    public static class BallisticSolution implements Serializable {
      @XmlElement(
          name = "POI"
      )
      protected DataMessage.Track.BallisticSolution.POI poi;
      @XmlElement(
          name = "POO"
      )
      protected DataMessage.Track.BallisticSolution.POO poo;
      @XmlAttribute(
          name = "final"
      )
      protected Boolean _final;

      public BallisticSolution() {
      }

      public DataMessage.Track.BallisticSolution.POI getPOI() {
        return this.poi;
      }

      public void setPOI(DataMessage.Track.BallisticSolution.POI value) {
        this.poi = value;
      }

      public DataMessage.Track.BallisticSolution.POO getPOO() {
        return this.poo;
      }

      public void setPOO(DataMessage.Track.BallisticSolution.POO value) {
        this.poo = value;
      }

      public Boolean isFinal() {
        return this._final;
      }

      public void setFinal(Boolean value) {
        this._final = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
          name = "",
          propOrder = {"xyzPos", "posError"}
      )
      public static class POO {
        @XmlElement(
            name = "XYZPos",
            required = true
        )
        protected XYZPos xyzPos;
        @XmlElement(
            name = "PosError"
        )
        protected PosError posError;

        public POO() {
        }

        public XYZPos getXYZPos() {
          return this.xyzPos;
        }

        public void setXYZPos(XYZPos value) {
          this.xyzPos = value;
        }

        public PosError getPosError() {
          return this.posError;
        }

        public void setPosError(PosError value) {
          this.posError = value;
        }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
          name = "",
          propOrder = {"xyzPos", "posError"}
      )
      public static class POI {
        @XmlElement(
            name = "XYZPos",
            required = true
        )
        protected XYZPos xyzPos;
        @XmlElement(
            name = "PosError"
        )
        protected PosError posError;

        public POI() {
        }

        public XYZPos getXYZPos() {
          return this.xyzPos;
        }

        public void setXYZPos(XYZPos value) {
          this.xyzPos = value;
        }

        public PosError getPosError() {
          return this.posError;
        }

        public void setPosError(PosError value) {
          this.posError = value;
        }
      }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = ""
    )
    public static class AcousticQualities {
      @XmlAttribute(
          name = "signal_type",
          required = true
      )
      protected String signalType;
      @XmlAttribute(
          name = "signal_conf"
      )
      protected Float signalConf;
      @XmlAttribute(
          name = "absolute_amplitude"
      )
      protected Double absoluteAmplitude;
      @XmlAttribute(
          name = "relative_amplitude"
      )
      protected Double relativeAmplitude;
      @XmlAttribute(
          name = "frequency"
      )
      protected Double frequency;
      @XmlAttribute(
          name = "fundamental_frequency"
      )
      protected Double fundamentalFrequency;

      public AcousticQualities() {
      }

      public String getSignalType() {
        return this.signalType;
      }

      public void setSignalType(String value) {
        this.signalType = value;
      }

      public Float getSignalConf() {
        return this.signalConf;
      }

      public void setSignalConf(Float value) {
        this.signalConf = value;
      }

      public Double getAbsoluteAmplitude() {
        return this.absoluteAmplitude;
      }

      public void setAbsoluteAmplitude(Double value) {
        this.absoluteAmplitude = value;
      }

      public Double getRelativeAmplitude() {
        return this.relativeAmplitude;
      }

      public void setRelativeAmplitude(Double value) {
        this.relativeAmplitude = value;
      }

      public Double getFrequency() {
        return this.frequency;
      }

      public void setFrequency(Double value) {
        this.frequency = value;
      }

      public Double getFundamentalFrequency() {
        return this.fundamentalFrequency;
      }

      public void setFundamentalFrequency(Double value) {
        this.fundamentalFrequency = value;
      }
    }
  }

  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(
      name = "",
      propOrder = {"component", "parent", "lob", "ecef", "classifications"}
  )
  public static class Threat implements Serializable  {
    @XmlElement(
        name = "Component"
    )
    protected List<DataMessage.Threat.Component> component;
    @XmlElement(
        name = "Parent"
    )
    protected List<DataMessage.Threat.Parent> parent;
    @XmlElement(
        name = "LOB"
    )
    protected DataMessage.Threat.LOB lob;
    @XmlElement(
        name = "ECEF"
    )
    protected DataMessage.Threat.ECEF ecef;
    @XmlElement(
        name = "Classifications"
    )
    protected Classifications classifications;
    @XmlAttribute(
        name = "update_time",
        required = true
    )
    protected BigInteger updateTime;
    @XmlAttribute(
        name = "threat_id",
        required = true
    )
    @XmlSchemaType(
        name = "unsignedInt"
    )
    protected long threatId;
    @XmlAttribute(
        name = "priority"
    )
    protected Float priority;
    @XmlAttribute(
        name = "affiliation"
    )
    protected Affiliation affiliation;
    @XmlAttribute(
        name = "verified"
    )
    protected Boolean verified;
    @XmlAttribute(
        name = "begin"
    )
    protected Boolean begin;
    @XmlAttribute(
        name = "end"
    )
    protected Boolean end;

    public Threat() {
    }

    public List<DataMessage.Threat.Component> getComponent() {
      if (this.component == null) {
        this.component = new ArrayList();
      }

      return this.component;
    }

    public List<DataMessage.Threat.Parent> getParent() {
      if (this.parent == null) {
        this.parent = new ArrayList();
      }

      return this.parent;
    }

    public DataMessage.Threat.LOB getLOB() {
      return this.lob;
    }

    public void setLOB(DataMessage.Threat.LOB value) {
      this.lob = value;
    }

    public DataMessage.Threat.ECEF getECEF() {
      return this.ecef;
    }

    public void setECEF(DataMessage.Threat.ECEF value) {
      this.ecef = value;
    }

    public Classifications getClassifications() {
      return this.classifications;
    }

    public void setClassifications(Classifications value) {
      this.classifications = value;
    }

    public BigInteger getUpdateTime() {
      return this.updateTime;
    }

    public void setUpdateTime(BigInteger value) {
      this.updateTime = value;
    }

    public long getThreatId() {
      return this.threatId;
    }

    public void setThreatId(long value) {
      this.threatId = value;
    }

    public Float getPriority() {
      return this.priority;
    }

    public void setPriority(Float value) {
      this.priority = value;
    }

    public Affiliation getAffiliation() {
      return this.affiliation;
    }

    public void setAffiliation(Affiliation value) {
      this.affiliation = value;
    }

    public Boolean isVerified() {
      return this.verified;
    }

    public void setVerified(Boolean value) {
      this.verified = value;
    }

    public Boolean isBegin() {
      return this.begin;
    }

    public void setBegin(Boolean value) {
      this.begin = value;
    }

    public Boolean isEnd() {
      return this.end;
    }

    public void setEnd(Boolean value) {
      this.end = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = ""
    )
    public static class Parent implements Serializable {
      @XmlAttribute(
          name = "threat_id",
          required = true
      )
      @XmlSchemaType(
          name = "unsignedInt"
      )
      protected long threatId;

      public Parent() {
      }

      public long getThreatId() {
        return this.threatId;
      }

      public void setThreatId(long value) {
        this.threatId = value;
      }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = "",
        propOrder = {"lineOfBearing", "originPos"}
    )
    public static class LOB implements Serializable {
      @XmlElement(
          name = "LineOfBearing",
          required = true
      )
      protected LineOfBearing lineOfBearing;
      @XmlElement(
          name = "OriginPos",
          required = true
      )
      protected XYZPos originPos;

      public LOB() {
      }

      public LineOfBearing getLineOfBearing() {
        return this.lineOfBearing;
      }

      public void setLineOfBearing(LineOfBearing value) {
        this.lineOfBearing = value;
      }

      public XYZPos getOriginPos() {
        return this.originPos;
      }

      public void setOriginPos(XYZPos value) {
        this.originPos = value;
      }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = "",
        propOrder = {"xyzPos", "posError"}
    )
    public static class ECEF implements Serializable {
      @XmlElement(
          name = "XYZPos",
          required = true
      )
      protected XYZPos xyzPos;
      @XmlElement(
          name = "PosError"
      )
      protected PosError posError;

      public ECEF() {
      }

      public XYZPos getXYZPos() {
        return this.xyzPos;
      }

      public void setXYZPos(XYZPos value) {
        this.xyzPos = value;
      }

      public PosError getPosError() {
        return this.posError;
      }

      public void setPosError(PosError value) {
        this.posError = value;
      }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = ""
    )
    public static class Component implements Serializable {
      @XmlAttribute(
          name = "sensor",
          required = true
      )
      protected String sensor;
      @XmlAttribute(
          name = "track_id",
          required = true
      )
      @XmlSchemaType(
          name = "unsignedInt"
      )
      protected long trackId;

      public Component() {
      }

      public String getSensor() {
        return this.sensor;
      }

      public void setSensor(String value) {
        this.sensor = value;
      }

      public long getTrackId() {
        return this.trackId;
      }

      public void setTrackId(long value) {
        this.trackId = value;
      }
    }
  }
}
