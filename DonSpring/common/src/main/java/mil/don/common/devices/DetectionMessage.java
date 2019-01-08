/*-------------------------------------------------------------------------------------------------
| *** UNCLASSIFIED ***
|--------------------------------------------------------------------------------------------------
| U.S. Army Research, Development, and Engineering Command
| Aviation and Missile Research, Development, and Engineering Center
| Software Engineering Directorate, Redstone Arsenal, AL
|--------------------------------------------------------------------------------------------------
| Export-Control Act Warning: Warning - This Document contains technical data whose export is
| restricted by the Arms Export Control Act (Title 22, U.S.C., Sec 2751, et seq) or the Export
| Administration Act of 1979, as amended, Title 50, U.S.C., App. 2401 et seq. Violations of these
| export laws are subject to severe criminal penalties. Disseminate in accordance with provisions
| of DoD Directive 5230.25.
|--------------------------------------------------------------------------------------------------
| Distribution Statement C:  Distribution authorized to U.S. Government Agencies and their
| contractors, Export Controlled, Critical Technology, 13 Feb 2017. Other request for this document
| shall be referred to U.S. Army Aviation and Missile Research Development and Engineering Center
| Software Engineering Directorate, Attn: RDMR-BAW, Redstone Arsenal, AL 35898.
--------------------------------------------------------------------------------------------------*/

package mil.don.common.devices;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mil.don.common.coordinates.EcefCoordinate;


// an event from a device in the system, reporting a detection has occurred
public class DetectionMessage implements Serializable
{

    // region LineOfBearing internal class ////////////////////////////////////

    public class LineOfBearing {

      protected float _azimuth;
      protected Float _elevation;
      protected Float _azimuthErr;
      protected Float _elevationErr;
      protected Float _minRange;
      protected Float _maxRange;

      public float getAzimuth() {
        return this._azimuth;
      }
      public void setAzimuth(float value) {
        this._azimuth = value;
      }
      public Float getElevation() {
        return this._elevation;
      }
      public void setElevation(Float value) {
        this._elevation = value;
      }
      public Float getAzimuthErr() {
        return this._azimuthErr;
      }
      public void setAzimuthErr(Float value) {
        this._azimuthErr = value;
      }
      public Float getElevationErr() {
        return this._elevationErr;
      }
      public void setElevationErr(Float value) {
        this._elevationErr = value;
      }
      public Float getMinRange() {
        return this._minRange;
      }
      public void setMinRange(Float value) {
        this._minRange = value;
      }
      public Float getMaxRange() {
        return this._maxRange;
      }
      public void setMaxRange(Float value) {
        this._maxRange = value;
      }
    }

    // endregion //////////////////////////////////////////////////////////////

    // region DetectionType enumeration ////////////////////////////////////////////

    public enum DetectionType {
        OTHER,
        ACOUSTIC,
        RADAR,
        EW,
        CAMERA,
        THREAT
    }

    // endregion //////////////////////////////////////////////////////////////

    // region Affiliation enumeration /////////////////////////////////////////

    public enum Affiliation
    {
      PENDING,
      UNKNOWN,
      ASSUMED_FRIEND,
      FRIEND,
      NEUTRAL,
      SUSPECT,
      HOSTILE
    }

    // endregion

    // region PositionCovariance internal class //////////////////////////

    public class PositionCovariance {

      private double _xx;
      private double _xy;
      private double _xz;
      private double _yy;
      private double _yz;
      private double _zz;

      public double getXx() {
        return this._xx;
      }
      public void setXx(double value) {
        this._xx = value;
      }
      public double getXy() {
        return this._xy;
      }
      public void setXy(double value) {
        this._xy = value;
      }
      public double getXz() {
        return this._xz;
      }
      public void setXz(double value) {
        this._xz = value;
      }
      public double getYy() {
        return this._yy;
      }
      public void setYy(double value) {
        this._yy = value;
      }
      public double getYz() {
        return this._yz;
      }
      public void setYz(double value) {
        this._yz = value;
      }
      public double getZz() {
        return this._zz;
      }
      public void setZz(double value) {
        this._zz = value;
      }
    }

    // endregion //////////////////////////////////////////////////////////////

    // region VelocityCovariance internal class /////////////////////////

    public class VelocityCovariance {


      protected double xx;
      protected double xy;
      protected double xz;
      protected double xXdot;
      protected double xYdot;
      protected double xZdot;
      protected double yy;
      protected double yz;
      protected double yXdot;
      protected double yYdot;
      protected double yZdot;
      protected double zz;
      protected double zXdot;
      protected double zYdot;
      protected double zZdot;
      protected double xdotXdot;
      protected double xdotYdot;
      protected double xdotZdot;
      protected double ydotYdot;
      protected double ydotZdot;
      protected double zdotZdot;

      public VelocityCovariance() {
      }

      public double getXX() {
        return this.xx;
      }
      public void setXX(double value) {
        this.xx = value;
      }
      public double getXY() {
        return this.xy;
      }
      public void setXY(double value) {
        this.xy = value;
      }
      public double getXZ() {
        return this.xz;
      }
      public void setXZ(double value) {
        this.xz = value;
      }
      public double getXXdot() {
        return this.xXdot;
      }
      public void setXXdot(double value) {
        this.xXdot = value;
      }
      public double getXYdot() {
        return this.xYdot;
      }
      public void setXYdot(double value) {
        this.xYdot = value;
      }
      public double getXZdot() {
        return this.xZdot;
      }
      public void setXZdot(double value) {
        this.xZdot = value;
      }
      public double getYY() {
        return this.yy;
      }
      public void setYY(double value) {
        this.yy = value;
      }
      public double getYZ() {
        return this.yz;
      }
      public void setYZ(double value) {
        this.yz = value;
      }
      public double getYXdot() {
        return this.yXdot;
      }
      public void setYXdot(double value) {
        this.yXdot = value;
      }
      public double getYYdot() {
        return this.yYdot;
      }
      public void setYYdot(double value) {
        this.yYdot = value;
      }
      public double getYZdot() {
        return this.yZdot;
      }
      public void setYZdot(double value) {
        this.yZdot = value;
      }
      public double getZZ() {
        return this.zz;
      }
      public void setZZ(double value) {
        this.zz = value;
      }
      public double getZXdot() {
        return this.zXdot;
      }
      public void setZXdot(double value) {
        this.zXdot = value;
      }
      public double getZYdot() {
        return this.zYdot;
      }
      public void setZYdot(double value) {
        this.zYdot = value;
      }
      public double getZZdot() {
        return this.zZdot;
      }
      public void setZZdot(double value) {
        this.zZdot = value;
      }
      public double getXdotXdot() {
        return this.xdotXdot;
      }
      public void setXdotXdot(double value) {
        this.xdotXdot = value;
      }
      public double getXdotYdot() {
        return this.xdotYdot;
      }
      public void setXdotYdot(double value) {
        this.xdotYdot = value;
      }
      public double getXdotZdot() {
        return this.xdotZdot;
      }
      public void setXdotZdot(double value) {
        this.xdotZdot = value;
      }
      public double getYdotYdot() {
        return this.ydotYdot;
      }
      public void setYdotYdot(double value) {
        this.ydotYdot = value;
      }
      public double getYdotZdot() {
        return this.ydotZdot;
      }
      public void setYdotZdot(double value) {
        this.ydotZdot = value;
      }
      public double getZdotZdot() {
        return this.zdotZdot;
      }
      public void setZdotZdot(double value) {
        this.zdotZdot = value;
      }
    }

    // endregion //////////////////////////////////////////////////////////////

    // region PositionReport internal class //////////////////////////////

    public class PositionReport {

      private EcefCoordinate _position;
      private EcefCoordinate _velocity;
      private PositionCovariance _positionCovariance;
      private VelocityCovariance _velocityCovariance;

      public EcefCoordinate getPosition() {
        return this._position;
      }
      public void setPosition(EcefCoordinate value) {
        this._position = value;
      }
      public EcefCoordinate getVelocity() {
        return this._velocity;
      }
      public void setVelocity(EcefCoordinate value) {
        this._velocity = value;
      }
      public PositionCovariance getPosCovariance() {
        return this._positionCovariance;
      }
      public void setPosCovariance(PositionCovariance value) {
        this._positionCovariance = value;
      }
      public VelocityCovariance getPosVelCovariance() {
        return this._velocityCovariance;
      }
      public void setPosVelCovariance(VelocityCovariance value) { this._velocityCovariance = value; }
    }

    // endregion //////////////////////////////////////////////////////////////

    // region RfQualities internal class ////////////////////////////////////////////

    public class RfQualities {

      protected String signalType;
      protected Boolean approaching;
      protected Double bandwidth;
      protected Integer channelCount;
      protected Float density;
      protected Integer detectionCount;
      protected Double frequency;
      protected Integer groupId;
      protected String macAddress;
      protected Float noiseStrength;
      protected Float pulseWidth;
      protected Float pri;
      protected Float signalConfidence;
      protected Float signalStrength;
      protected String targetVideoStream;

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
        return this.signalConfidence;
      }
      public void setSignalConf(Float value) {
        this.signalConfidence = value;
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

  // endregion //////////////////////////////////////////////////////////////

    // region Track internal class ////////////////////////////////////////////

    public class Track {

      private LineOfBearing _lineOfBearing;
      private PositionReport _position;
      private RfQualities _rfQualities;
      private long _trackId;
      private BigInteger _timestamp;
      private boolean _isBeginning;
      private boolean _isEnding;
      private Affiliation _affiliation;

      public LineOfBearing getLineOfBearing() {
        return this._lineOfBearing;
      }
      public void setLineOfBearing(LineOfBearing value) {
        this._lineOfBearing = value;
      }

      public PositionReport getPosition() {
        return this._position;
      }
      public void setPosition(PositionReport value) { this._position = value; }

      public RfQualities getRFQualities() {
        return this._rfQualities;
      }
      public void setRFQualities(RfQualities value) {
        this._rfQualities = value;
      }

      public long getTrackId() {
        return this._trackId;
      }
      public void setTrackId(long value) {
        this._trackId = value;
      }

      public BigInteger getUpdateTime() {
        return this._timestamp;
      }
      public void setUpdateTime(BigInteger value) {
        this._timestamp = value;
      }

      public Boolean isBeginning() {
        return this._isBeginning;
      }
      public void setBeginning(Boolean value) {
        this._isBeginning = value;
      }

      public Boolean isEnd() {
        return this._isEnding;
      }
      public void setEnd(Boolean value) {
        this._isEnding = value;
      }

      public Affiliation getAffiliation() {
        return this._affiliation;
      }
      public void setAffiliation(Affiliation value) {
        this._affiliation = value;
      }
    }

    // endregion //////////////////////////////////////////////////////////////

    // region ThreatComponent internal class ///////////////////////////////////////////

    public class ThreatComponent {
      protected String sensor;
      protected long trackId;

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

    // endregion //////////////////////////////////////////////////////////////

    // region ThreatParent internal class ///////////////////////////////////////////

    public class ThreatParent {
      protected long threatId;

      public long getThreatId() {
        return this.threatId;
      }
      public void setThreatId(long value) {
        this.threatId = value;
      }
    }

    // endregion //////////////////////////////////////////////////////////////

    // region Threat internal class ///////////////////////////////////////////

    public class Threat {

      private List<ThreatComponent> _components;
      private ThreatParent _parent;

    }

    // endregion //////////////////////////////////////////////////////////////

    private String _revision;
    private String _sourceSystem;
    private DetectionType _detectionType;
    private String _id;
    private long _index = 1;
    private Date _timestamp;
    private boolean _isTimestampValid;
    private String _sourceDeviceName;
    private String _sourceDeviceType;

    private final Map<String, String> _properties = new HashMap<>();





    public DetectionType getDetectionType()
    {
        return _detectionType;
    }
    public DetectionMessage setDetectionType(DetectionType detectionType) { this._detectionType = detectionType; return this; }

    public String getId()
    {
        return _id;
    }
    public DetectionMessage setId(String id) { this._id = id; return this; }

    public Date getTimestamp()
    {
        return _timestamp;
    }
    public DetectionMessage setTimestamp(Date timestamp) { this._timestamp = timestamp; return this; }

    public boolean getIsTimestampValid()
  {
    return _isTimestampValid;
  }
    public DetectionMessage setIsTimestampValid(boolean isTimestampValid) { this._isTimestampValid = isTimestampValid; return this; }

    public String getSourceName() {
        return _sourceDeviceName;
    }
    public DetectionMessage setSourceName(String sourceName) { this._sourceDeviceName = sourceName; return this; }

    public String getSourceDeviceType() {
        return _sourceDeviceType;
    }
    public DetectionMessage setSourceDeviceType(String deviceType) { this._sourceDeviceType = deviceType; return this; }

    public long getIndex() { return _index; }
    public DetectionMessage setIndex(int index) { _index = index; return this; }

    public Map<String, String> getProperties()
    {
        return _properties;
    }

    @Override
    public String toString() {
        return String.format("device.detection last='%tc', idx='%d', type='%s', source='%s', source_type='%s'",
            _timestamp, _index, _detectionType, _sourceDeviceName, _sourceDeviceType);
    }
}
