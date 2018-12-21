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

package mil.don.common.messages.Ctc;

import java.util.ArrayList;

public class VeTrackUpdateMessage extends VeMessage
{
  protected static final double VELOCITY_SCALE = 0.125; // 0.125 meter/second
  protected static final double ECEF_SCALE = 1d;      // 1 meter

  private static final int SENSOR_DATA_SIZE = 4;


  // TRACK_UPDATE_BLOCK
  private static final int CT_TRACK_INDEX = BLOCK_ID_INDEX + 1;      // unsigned short
  private static final int REMOTE_TRACK_INDEX = CT_TRACK_INDEX + 2;    // unsigned short
  private static final int RADAR_INDEX = REMOTE_TRACK_INDEX + 4;      // unsigned char
  private static final int SYMBOL_INDEX = RADAR_INDEX + 1;        // unsigned char
  private static final int MISS_COUNTER_INDEX = SYMBOL_INDEX + 1;      // unsigned char
  private static final int SPARE1_INDEX = MISS_COUNTER_INDEX + 1;    // unsigned char
  private static final int TRACK_TIME_INDEX = SPARE1_INDEX + 1;      // unsigned short (MSW) / unsigned short (LSW)
  private static final int SMOOTH_X_INDEX = TRACK_TIME_INDEX + 4;      // long
  private static final int SMOOTH_Y_INDEX = SMOOTH_X_INDEX + 4;      // long
  private static final int SMOOTH_Z_INDEX = SMOOTH_Y_INDEX + 4;      // long
  private static final int SMOOTH_X_DOT_INDEX = SMOOTH_Z_INDEX + 4;    // short
  private static final int SMOOTH_Y_DOT_INDEX = SMOOTH_X_DOT_INDEX + 2;  // short
  private static final int SMOOTH_Z_DOT_INDEX = SMOOTH_Y_DOT_INDEX + 2;  // short
  private static final int MINIMUM_BYTES = SMOOTH_Z_DOT_INDEX + 2;
  private static final int SUBSTRUCTA6_INDEX = SMOOTH_Z_DOT_INDEX + 2;
  private static final int SUBSTRUCTB6_INDEX = SUBSTRUCTA6_INDEX + 4;
  private static final int SUBSTRUCTC6_INDEX = SUBSTRUCTB6_INDEX + 4;
  private static final int SUBSTRUCTD6_INDEX = SUBSTRUCTC6_INDEX + 4;
  private static final int SUBSTRUCTE6_INDEX = SUBSTRUCTD6_INDEX + 4;
  private static final int SUBSTRUCTF6_INDEX = SUBSTRUCTE6_INDEX + 4;
  private static final int SUBSTRUCTG6_INDEX = SUBSTRUCTF6_INDEX + 4;
  private static final int SUBSTRUCTH6_INDEX = SUBSTRUCTG6_INDEX + 4;
  private static final int TRUTH_ID_INDEX = SUBSTRUCTH6_INDEX + 4;
  private static final int RCS_INDEX = TRUTH_ID_INDEX + 8;
  private static final int SUBSTRUCTD3_INDEX = RCS_INDEX + 12;
  private static final int RMS_TYPE_INDEX = SUBSTRUCTD3_INDEX + 2;
  private static final int TARGET_LENGTH_INDEX = RMS_TYPE_INDEX + 2;
  private static final int CLASSIFICATION_INDEX = TARGET_LENGTH_INDEX + 2;
  private static final int SPARE2_INDEX = CLASSIFICATION_INDEX + 2;
  private static final int NUMBER_OF_SENSORS_INDEX = SPARE2_INDEX + 1;
  private static final int SENSOR_DATA_INDEX = NUMBER_OF_SENSORS_INDEX + 1;


  private VeTrackUpdateMessage(VeTrackUpdateMessageBuilder builder)
  {
    super(builder);
  }

  public Long getRemoteTrackId()
  {
    Long returnValue = null;
    if (isTrackUpdate())
    {
      returnValue = convertFourBytesToInt(messageBody[REMOTE_TRACK_INDEX],
          messageBody[REMOTE_TRACK_INDEX + 1],
          messageBody[REMOTE_TRACK_INDEX + 2],
          messageBody[REMOTE_TRACK_INDEX + 3]);
    }
    return returnValue;
  }

  public Integer getRadar()
  {
    return isTrackUpdate() ? messageBody[RADAR_INDEX] & 0xFF : null;
  }

  /**
   * Gets the time of update
   *
   * @return seconds since midnight
   */
  public Double getTimeSinceMidnight()
  {
    Double result = null;
    if (isTrackUpdate())
    {
      Long t = convertFourBytesToTime(messageBody[TRACK_TIME_INDEX],
          messageBody[TRACK_TIME_INDEX + 1],
          messageBody[TRACK_TIME_INDEX + 2],
          messageBody[TRACK_TIME_INDEX + 3]);
      result = t.doubleValue();
    }
    // The time is reported in hundreds of us so convert to second by dividing by 10,000
    return result != null ? result / TIME_SCALE : null;
  }

  public Integer getCTTrackId()
  {
    Integer retVal = null;
    if (isTrackUpdate() || isTrackDrop())
    {
      int id = messageBody[CT_TRACK_INDEX + 1] & 0xFF;
      retVal = (id << 8) | messageBody[CT_TRACK_INDEX] & 0xFF;

    }
    return retVal;
  }

  public Long getX()
  {
    Long result = null;
    if (isTrackUpdate())
    {
      result = (long) (convertFourBytesToInt(messageBody[SMOOTH_X_INDEX],
          messageBody[SMOOTH_X_INDEX + 1],
          messageBody[SMOOTH_X_INDEX + 2],
          messageBody[SMOOTH_X_INDEX + 3]) * ECEF_SCALE);
    }
    return result;
  }

  public Long getY()
  {
    Long result = null;
    if (isTrackUpdate())
    {
      result = (long) (convertFourBytesToInt(messageBody[SMOOTH_Y_INDEX],
          messageBody[SMOOTH_Y_INDEX + 1],
          messageBody[SMOOTH_Y_INDEX + 2],
          messageBody[SMOOTH_Y_INDEX + 3]) * ECEF_SCALE);
    }
    return result;
  }

  public Long getZ()
  {
    Long result = null;
    if (isTrackUpdate())
    {
      result = (long) (convertFourBytesToInt(messageBody[SMOOTH_Z_INDEX],
          messageBody[SMOOTH_Z_INDEX + 1],
          messageBody[SMOOTH_Z_INDEX + 2],
          messageBody[SMOOTH_Z_INDEX + 3]) * ECEF_SCALE);
    }
    return result;

  }


  public Double getXDot()
  {
    Double result = null;
    if (isTrackUpdate())
    {
      result = (double) convertTwoBytesToInt(messageBody[SMOOTH_X_DOT_INDEX],
          messageBody[SMOOTH_X_DOT_INDEX + 1]) * VELOCITY_SCALE;
    }
    return result;
  }

  public Double getYDot()
  {
    Double result = null;
    if (isTrackUpdate())
    {
      result = (double) convertTwoBytesToInt(messageBody[SMOOTH_Y_DOT_INDEX],
          messageBody[SMOOTH_Y_DOT_INDEX + 1]) * VELOCITY_SCALE;
    }
    return result;
  }

  public Double getZDot()
  {
    Double result = null;
    if (isTrackUpdate())
    {
      result = (double) convertTwoBytesToInt(messageBody[SMOOTH_Z_DOT_INDEX],
          messageBody[SMOOTH_Z_DOT_INDEX + 1]) * VELOCITY_SCALE;
    }
    return result;
  }

  /**
   * Returns the number of sensors that contributed to the current track
   * @return The number of sensors
   */
  public int getNumberOfSensors()
  {
    int result = 0;
    if(messageBody.length > NUMBER_OF_SENSORS_INDEX + 1)
    {
      result = convertTwoBytesToInt(messageBody[NUMBER_OF_SENSORS_INDEX],
          messageBody[NUMBER_OF_SENSORS_INDEX + 1]);
    }

    return result;
  }

  public ArrayList<Integer> getListOfSensorIds()
  {
    ArrayList<Integer> result = new ArrayList<>();
    int curSensor = SENSOR_DATA_INDEX;

    for(int i = 0; i < getNumberOfSensors(); i++)
    {
      if(curSensor < messageBody.length)
      {
        int tmp = messageBody[curSensor + 1];
        result.add(tmp);
        curSensor += SENSOR_DATA_SIZE;
      }
    }

    return result;
  }

  public static class VeTrackUpdateMessageBuilder extends VeMessageBuilder
  {
    /**
     * Constructor
     */
    public VeTrackUpdateMessageBuilder()
    {
    }

    /**
     * Constructor from raw VE message
     *
     * @param message - the VE message data to use
     */
    public VeTrackUpdateMessageBuilder(CtcMessage message)
    {
      super(message.getBytes());
    }

    /**
     * Constructor from raw VE message
     *
     * @param messageBody - the VE message data to use
     */
    public VeTrackUpdateMessageBuilder(byte[] messageBody)
    {
      super(messageBody);
    }

    /**
     * Creates instance of VE message
     *
     * @return SwitchBladeMessage
     */
    public VeTrackUpdateMessage build()
    {
      boolean result = (messageBody != null);
      if (result && messageBody.length > 2)
      {
        int blockId = messageBody[BLOCK_ID_INDEX] & 0xFF;
        result =
            (blockId == TRACK_UPDATE_BLOCK && messageBody.length >= MINIMUM_BYTES);
      }

      return result ? new VeTrackUpdateMessage(this) : null;
    }
  }
}
