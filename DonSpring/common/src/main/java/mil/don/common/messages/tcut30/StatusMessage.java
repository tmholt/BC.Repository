
package mil.don.common.messages.tcut30;


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
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "",
    propOrder = {"status", "log"}
)
@XmlRootElement(
    name = "StatusMessage"
)
public class StatusMessage {
  @XmlElement(
      name = "Status"
  )
  protected StatusMessage.Status status;
  @XmlElement(
      name = "Log"
  )
  protected List<StatusMessage.Log> log;
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

  public StatusMessage() {
  }

  public StatusMessage.Status getStatus() {
    return this.status;
  }

  public void setStatus(StatusMessage.Status value) {
    this.status = value;
  }

  public List<StatusMessage.Log> getLog() {
    if (this.log == null) {
      this.log = new ArrayList();
    }

    return this.log;
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
      propOrder = {"spatialFactors", "opticalFactors", "rfFactors", "coordinationFactors"}
  )
  public static class Status {
    @XmlElement(
        name = "SpatialFactors"
    )
    protected StatusMessage.Status.SpatialFactors spatialFactors;
    @XmlElement(
        name = "OpticalFactors"
    )
    protected StatusMessage.Status.OpticalFactors opticalFactors;
    @XmlElement(
        name = "RFFactors"
    )
    protected StatusMessage.Status.RFFactors rfFactors;
    @XmlElement(
        name = "CoordinationFactors"
    )
    protected StatusMessage.Status.CoordinationFactors coordinationFactors;
    @XmlAttribute(
        name = "overall_status",
        required = true
    )
    protected BitResultStatusE overallStatus;
    @XmlAttribute(
        name = "mission",
        required = true
    )
    protected String mission;
    @XmlAttribute(
        name = "command_mode"
    )
    protected CommandModeE commandMode;
    @XmlAttribute(
        name = "system_state"
    )
    protected EwMode systemState;
    @XmlAttribute(
        name = "sw_version"
    )
    protected String swVersion;
    @XmlAttribute(
        name = "hw_version"
    )
    protected String hwVersion;

    public Status() {
    }

    public StatusMessage.Status.SpatialFactors getSpatialFactors() {
      return this.spatialFactors;
    }

    public void setSpatialFactors(StatusMessage.Status.SpatialFactors value) {
      this.spatialFactors = value;
    }

    public StatusMessage.Status.OpticalFactors getOpticalFactors() {
      return this.opticalFactors;
    }

    public void setOpticalFactors(StatusMessage.Status.OpticalFactors value) {
      this.opticalFactors = value;
    }

    public StatusMessage.Status.RFFactors getRFFactors() {
      return this.rfFactors;
    }

    public void setRFFactors(StatusMessage.Status.RFFactors value) {
      this.rfFactors = value;
    }

    public StatusMessage.Status.CoordinationFactors getCoordinationFactors() {
      return this.coordinationFactors;
    }

    public void setCoordinationFactors(StatusMessage.Status.CoordinationFactors value) {
      this.coordinationFactors = value;
    }

    public BitResultStatusE getOverallStatus() {
      return this.overallStatus;
    }

    public void setOverallStatus(BitResultStatusE value) {
      this.overallStatus = value;
    }

    public String getMission() {
      return this.mission;
    }

    public void setMission(String value) {
      this.mission = value;
    }

    public CommandModeE getCommandMode() {
      return this.commandMode;
    }

    public void setCommandMode(CommandModeE value) {
      this.commandMode = value;
    }

    public EwMode getSystemState() {
      return this.systemState;
    }

    public void setSystemState(EwMode value) {
      this.systemState = value;
    }

    public String getSwVersion() {
      return this.swVersion;
    }

    public void setSwVersion(String value) {
      this.swVersion = value;
    }

    public String getHwVersion() {
      return this.hwVersion;
    }

    public void setHwVersion(String value) {
      this.hwVersion = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = "",
        propOrder = {"emplacement", "orientation", "referencePos", "coverageSector"}
    )
    public static class SpatialFactors {
      @XmlElement(
          name = "Emplacement"
      )
      protected XYZPos emplacement;
      @XmlElement(
          name = "Orientation"
      )
      protected StatusMessage.Status.SpatialFactors.Orientation orientation;
      @XmlElement(
          name = "ReferencePos"
      )
      protected XYZPos referencePos;
      @XmlElement(
          name = "CoverageSector"
      )
      protected List<StatusMessage.Status.SpatialFactors.CoverageSector> coverageSector;
      @XmlAttribute(
          name = "gps_locked"
      )
      protected Boolean gpsLocked;
      @XmlAttribute(
          name = "gps_keyed"
      )
      protected Boolean gpsKeyed;

      public SpatialFactors() {
      }

      public XYZPos getEmplacement() {
        return this.emplacement;
      }

      public void setEmplacement(XYZPos value) {
        this.emplacement = value;
      }

      public StatusMessage.Status.SpatialFactors.Orientation getOrientation() {
        return this.orientation;
      }

      public void setOrientation(StatusMessage.Status.SpatialFactors.Orientation value) {
        this.orientation = value;
      }

      public XYZPos getReferencePos() {
        return this.referencePos;
      }

      public void setReferencePos(XYZPos value) {
        this.referencePos = value;
      }

      public List<StatusMessage.Status.SpatialFactors.CoverageSector> getCoverageSector() {
        if (this.coverageSector == null) {
          this.coverageSector = new ArrayList();
        }

        return this.coverageSector;
      }

      public Boolean isGpsLocked() {
        return this.gpsLocked;
      }

      public void setGpsLocked(Boolean value) {
        this.gpsLocked = value;
      }

      public Boolean isGpsKeyed() {
        return this.gpsKeyed;
      }

      public void setGpsKeyed(Boolean value) {
        this.gpsKeyed = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
          name = ""
      )
      public static class Orientation {
        @XmlAttribute(
            name = "_azimuth",
            required = true
        )
        protected float azimuth;
        @XmlAttribute(
            name = "_elevation",
            required = true
        )
        protected float elevation;

        public Orientation() {
        }

        public float getAzimuth() {
          return this.azimuth;
        }

        public void setAzimuth(float value) {
          this.azimuth = value;
        }

        public float getElevation() {
          return this.elevation;
        }

        public void setElevation(float value) {
          this.elevation = value;
        }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
          name = ""
      )
      public static class CoverageSector {
        @XmlAttribute(
            name = "start_range",
            required = true
        )
        protected double startRange;
        @XmlAttribute(
            name = "end_range",
            required = true
        )
        protected double endRange;
        @XmlAttribute(
            name = "start_azimuth",
            required = true
        )
        protected float startAzimuth;
        @XmlAttribute(
            name = "end_azimuth",
            required = true
        )
        protected float endAzimuth;
        @XmlAttribute(
            name = "start_elevation",
            required = true
        )
        protected float startElevation;
        @XmlAttribute(
            name = "end_elevation",
            required = true
        )
        protected float endElevation;

        public CoverageSector() {
        }

        public double getStartRange() {
          return this.startRange;
        }

        public void setStartRange(double value) {
          this.startRange = value;
        }

        public double getEndRange() {
          return this.endRange;
        }

        public void setEndRange(double value) {
          this.endRange = value;
        }

        public float getStartAzimuth() {
          return this.startAzimuth;
        }

        public void setStartAzimuth(float value) {
          this.startAzimuth = value;
        }

        public float getEndAzimuth() {
          return this.endAzimuth;
        }

        public void setEndAzimuth(float value) {
          this.endAzimuth = value;
        }

        public float getStartElevation() {
          return this.startElevation;
        }

        public void setStartElevation(float value) {
          this.startElevation = value;
        }

        public float getEndElevation() {
          return this.endElevation;
        }

        public void setEndElevation(float value) {
          this.endElevation = value;
        }
      }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = "",
        propOrder = {"frequency", "ecm"}
    )
    public static class RFFactors {
      @XmlElement(
          name = "Frequency"
      )
      protected List<StatusMessage.Status.RFFactors.Frequency> frequency;
      @XmlElement(
          name = "ECM"
      )
      protected StatusMessage.Status.RFFactors.ECM ecm;

      public RFFactors() {
      }

      public List<StatusMessage.Status.RFFactors.Frequency> getFrequency() {
        if (this.frequency == null) {
          this.frequency = new ArrayList();
        }

        return this.frequency;
      }

      public StatusMessage.Status.RFFactors.ECM getECM() {
        return this.ecm;
      }

      public void setECM(StatusMessage.Status.RFFactors.ECM value) {
        this.ecm = value;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
          name = ""
      )
      public static class Frequency {
        @XmlAttribute(
            name = "value",
            required = true
        )
        protected float value;
        @XmlAttribute(
            name = "transmitting",
            required = true
        )
        protected boolean transmitting;
        @XmlAttribute(
            name = "jamming_status"
        )
        protected JammingStatusE jammingStatus;

        public Frequency() {
        }

        public float getValue() {
          return this.value;
        }

        public void setValue(float value) {
          this.value = value;
        }

        public boolean isTransmitting() {
          return this.transmitting;
        }

        public void setTransmitting(boolean value) {
          this.transmitting = value;
        }

        public JammingStatusE getJammingStatus() {
          return this.jammingStatus;
        }

        public void setJammingStatus(JammingStatusE value) {
          this.jammingStatus = value;
        }
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
          name = "",
          propOrder = {"ecmEndPosition", "ecmCurrentPosition", "ecmStartPosition", "candidatePosition"}
      )
      public static class ECM {
        @XmlElement(
            name = "ECMEndPosition"
        )
        protected XYZPos ecmEndPosition;
        @XmlElement(
            name = "ECMCurrentPosition"
        )
        protected XYZPos ecmCurrentPosition;
        @XmlElement(
            name = "ECMStartPosition"
        )
        protected XYZPos ecmStartPosition;
        @XmlElement(
            name = "CandidatePosition"
        )
        protected XYZPos candidatePosition;
        @XmlAttribute(
            name = "frequency_is_stable"
        )
        protected Boolean frequencyIsStable;
        @XmlAttribute(
            name = "ecm_type"
        )
        protected String ecmType;
        @XmlAttribute(
            name = "ecm_state"
        )
        protected EcmMode ecmState;
        @XmlAttribute(
            name = "locked_to_candidate"
        )
        protected Boolean lockedToCandidate;

        public ECM() {
        }

        public XYZPos getECMEndPosition() {
          return this.ecmEndPosition;
        }

        public void setECMEndPosition(XYZPos value) {
          this.ecmEndPosition = value;
        }

        public XYZPos getECMCurrentPosition() {
          return this.ecmCurrentPosition;
        }

        public void setECMCurrentPosition(XYZPos value) {
          this.ecmCurrentPosition = value;
        }

        public XYZPos getECMStartPosition() {
          return this.ecmStartPosition;
        }

        public void setECMStartPosition(XYZPos value) {
          this.ecmStartPosition = value;
        }

        public XYZPos getCandidatePosition() {
          return this.candidatePosition;
        }

        public void setCandidatePosition(XYZPos value) {
          this.candidatePosition = value;
        }

        public Boolean isFrequencyIsStable() {
          return this.frequencyIsStable;
        }

        public void setFrequencyIsStable(Boolean value) {
          this.frequencyIsStable = value;
        }

        public String getEcmType() {
          return this.ecmType;
        }

        public void setEcmType(String value) {
          this.ecmType = value;
        }

        public EcmMode getEcmState() {
          return this.ecmState;
        }

        public void setEcmState(EcmMode value) {
          this.ecmState = value;
        }

        public Boolean isLockedToCandidate() {
          return this.lockedToCandidate;
        }

        public void setLockedToCandidate(Boolean value) {
          this.lockedToCandidate = value;
        }
      }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = "",
        propOrder = {"subcameraStatus"}
    )
    public static class OpticalFactors {
      @XmlElement(
          name = "SubcameraStatus"
      )
      protected List<StatusMessage.Status.OpticalFactors.SubcameraStatus> subcameraStatus;

      public OpticalFactors() {
      }

      public List<StatusMessage.Status.OpticalFactors.SubcameraStatus> getSubcameraStatus() {
        if (this.subcameraStatus == null) {
          this.subcameraStatus = new ArrayList();
        }

        return this.subcameraStatus;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
          name = ""
      )
      public static class SubcameraStatus {
        @XmlAttribute(
            name = "subcamera_id",
            required = true
        )
        protected String subcameraId;
        @XmlAttribute(
            name = "camera_type",
            required = true
        )
        protected CameraType cameraType;
        @XmlAttribute(
            name = "zoom_level",
            required = true
        )
        protected float zoomLevel;
        @XmlAttribute(
            name = "focus_level"
        )
        protected Float focusLevel;
        @XmlAttribute(
            name = "min_fov",
            required = true
        )
        protected float minFov;
        @XmlAttribute(
            name = "max_fov",
            required = true
        )
        protected float maxFov;
        @XmlAttribute(
            name = "autofocus_available",
            required = true
        )
        protected boolean autofocusAvailable;
        @XmlAttribute(
            name = "tracking_target"
        )
        protected Boolean trackingTarget;

        public SubcameraStatus() {
        }

        public String getSubcameraId() {
          return this.subcameraId;
        }

        public void setSubcameraId(String value) {
          this.subcameraId = value;
        }

        public CameraType getCameraType() {
          return this.cameraType;
        }

        public void setCameraType(CameraType value) {
          this.cameraType = value;
        }

        public float getZoomLevel() {
          return this.zoomLevel;
        }

        public void setZoomLevel(float value) {
          this.zoomLevel = value;
        }

        public Float getFocusLevel() {
          return this.focusLevel;
        }

        public void setFocusLevel(Float value) {
          this.focusLevel = value;
        }

        public float getMinFov() {
          return this.minFov;
        }

        public void setMinFov(float value) {
          this.minFov = value;
        }

        public float getMaxFov() {
          return this.maxFov;
        }

        public void setMaxFov(float value) {
          this.maxFov = value;
        }

        public boolean isAutofocusAvailable() {
          return this.autofocusAvailable;
        }

        public void setAutofocusAvailable(boolean value) {
          this.autofocusAvailable = value;
        }

        public Boolean isTrackingTarget() {
          return this.trackingTarget;
        }

        public void setTrackingTarget(Boolean value) {
          this.trackingTarget = value;
        }
      }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(
        name = "",
        propOrder = {"targetCue"}
    )
    public static class CoordinationFactors {
      @XmlElement(
          name = "TargetCue"
      )
      protected List<StatusMessage.Status.CoordinationFactors.TargetCue> targetCue;

      public CoordinationFactors() {
      }

      public List<StatusMessage.Status.CoordinationFactors.TargetCue> getTargetCue() {
        if (this.targetCue == null) {
          this.targetCue = new ArrayList();
        }

        return this.targetCue;
      }

      @XmlAccessorType(XmlAccessType.FIELD)
      @XmlType(
          name = ""
      )
      public static class TargetCue {
        @XmlAttribute(
            name = "cue_id"
        )
        @XmlSchemaType(
            name = "unsignedInt"
        )
        protected Long cueId;
        @XmlAttribute(
            name = "system_to_cue",
            required = true
        )
        protected String systemToCue;
        @XmlAttribute(
            name = "target_id",
            required = true
        )
        @XmlSchemaType(
            name = "unsignedShort"
        )
        protected int targetId;
        @XmlAttribute(
            name = "target_source_system",
            required = true
        )
        protected String targetSourceSystem;

        public TargetCue() {
        }

        public Long getCueId() {
          return this.cueId;
        }

        public void setCueId(Long value) {
          this.cueId = value;
        }

        public String getSystemToCue() {
          return this.systemToCue;
        }

        public void setSystemToCue(String value) {
          this.systemToCue = value;
        }

        public int getTargetId() {
          return this.targetId;
        }

        public void setTargetId(int value) {
          this.targetId = value;
        }

        public String getTargetSourceSystem() {
          return this.targetSourceSystem;
        }

        public void setTargetSourceSystem(String value) {
          this.targetSourceSystem = value;
        }
      }
    }
  }

  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(
      name = "",
      propOrder = {"value"}
  )
  public static class Log {
    @XmlValue
    protected String value;
    @XmlAttribute(
        name = "severity",
        required = true
    )
    protected LogLevelE severity;
    @XmlAttribute(
        name = "error_code"
    )
    @XmlSchemaType(
        name = "unsignedShort"
    )
    protected Integer errorCode;
    @XmlAttribute(
        name = "time",
        required = true
    )
    protected BigInteger time;
    @XmlAttribute(
        name = "silent"
    )
    protected Boolean silent;

    public Log() {
    }

    public String getValue() {
      return this.value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    public LogLevelE getSeverity() {
      return this.severity;
    }

    public void setSeverity(LogLevelE value) {
      this.severity = value;
    }

    public Integer getErrorCode() {
      return this.errorCode;
    }

    public void setErrorCode(Integer value) {
      this.errorCode = value;
    }

    public BigInteger getTime() {
      return this.time;
    }

    public void setTime(BigInteger value) {
      this.time = value;
    }

    public Boolean isSilent() {
      return this.silent;
    }

    public void setSilent(Boolean value) {
      this.silent = value;
    }
  }
}
