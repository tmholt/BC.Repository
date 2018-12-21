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

public class VeTrackerLocationMessage extends VeMessage
{
  // TRACKER_LOCATION_BLOCK
  private static final int UNIX_TIME_MSW_INDEX = BLOCK_ID_INDEX + 3;      // char + 2 spare char
  private static final int UNIX_TIME_LSW_INDEX = UNIX_TIME_MSW_INDEX + 4;    // long
  private static final int UNIX_TIME_NS_INDEX = UNIX_TIME_LSW_INDEX + 4;    // long
  private static final int TRACKER_X_INDEX = UNIX_TIME_NS_INDEX + 4;    // long
  private static final int TRACKER_Y_INDEX = TRACKER_X_INDEX + 4;      // long
  private static final int TRACKER_Z_INDEX = TRACKER_Y_INDEX + 4;      // long
  private static final int MINIMUM_BYTES = TRACKER_Z_INDEX + 4;

  private VeTrackerLocationMessage(VeTrackerLocationMessageBuilder builder)
  {
    super(builder);
  }

  public Long getX()
  {
    Long result = null;
    if (isTrackerLocation())
    {
      result = convertFourBytesToInt(messageBody[TRACKER_X_INDEX],
          messageBody[TRACKER_X_INDEX + 1],
          messageBody[TRACKER_X_INDEX + 2],
          messageBody[TRACKER_X_INDEX + 3]);
    }
    return result;
  }

  public Long getY()
  {
    Long result = null;
    if (isTrackerLocation())
    {
      result = convertFourBytesToInt(messageBody[TRACKER_Y_INDEX],
          messageBody[TRACKER_Y_INDEX + 1],
          messageBody[TRACKER_Y_INDEX + 2],
          messageBody[TRACKER_Y_INDEX + 3]);
    }
    return result;
  }

  public Long getZ()
  {
    Long result = null;
    if (isTrackerLocation())
    {
      result = convertFourBytesToInt(messageBody[TRACKER_Z_INDEX],
          messageBody[TRACKER_Z_INDEX + 1],
          messageBody[TRACKER_Z_INDEX + 2],
          messageBody[TRACKER_Z_INDEX + 3]);
    }
    return result;

  }

  public static class VeTrackerLocationMessageBuilder extends VeMessageBuilder
  {
    /**
     * Constructor
     */
    public VeTrackerLocationMessageBuilder()
    {
    }

    /**
     * Constructor from raw VE message
     *
     * @param message - the VE message data to use
     */
    public VeTrackerLocationMessageBuilder(CtcMessage message)
    {
      super(message.getBytes());
    }

    /**
     * Constructor from raw VE message
     *
     * @param messageBody - the VE message data to use
     */
    public VeTrackerLocationMessageBuilder(byte[] messageBody)
    {
      super(messageBody);
    }

    /**
     * Creates instance of VE message
     *
     * @return SwitchBladeMessage
     */
    public VeTrackerLocationMessage build()
    {
      boolean result = (messageBody != null);
      if (result && messageBody.length > 2)
      {
        int blockId = messageBody[BLOCK_ID_INDEX] & 0xFF;
        result =
            (blockId == TRACKER_LOCATION_BLOCK && messageBody.length >= MINIMUM_BYTES);
      }

      return result ? new VeTrackerLocationMessage(this) : null;
    }
  }
}
