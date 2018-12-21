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

public class VeDropMessage extends VeMessage
{
  // TRACK_DROP_BLOCK
  private static final int CT_TRACK_INDEX = BLOCK_ID_INDEX + 1;      // unsigned short
  private static final int TRACK_DROP_TIME_INDEX = CT_TRACK_INDEX + 2;  // unsigned short (MSW) / unsigned short (LSW)
  private static final int MINIMUM_BYTES = TRACK_DROP_TIME_INDEX + 4;

  private VeDropMessage(VeDropMessageBuilder builder)
  {
    super(builder);
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

  /**
   * Gets the time of update
   *
   * @return seconds since midnight
   */
  public Double getTimeSinceMidnight()
  {
    Double result = null;
    if (isTrackDrop())
    {
      Long t = convertFourBytesToTime(messageBody[TRACK_DROP_TIME_INDEX],
          messageBody[TRACK_DROP_TIME_INDEX + 1],
          messageBody[TRACK_DROP_TIME_INDEX + 2],
          messageBody[TRACK_DROP_TIME_INDEX + 3]);
      result = t.doubleValue();

    }
    // The time is reported in hundreds of us so convert to second by dividing by 10,000
    return result != null ? result / TIME_SCALE : null;
  }

  public static class VeDropMessageBuilder extends VeMessage.VeMessageBuilder
  {
    /**
     * Constructor
     */
    public VeDropMessageBuilder()
    {
    }

    /**
     * Constructor from raw VE message
     *
     * @param message - the VE message data to use
     */
    public VeDropMessageBuilder(CtcMessage message)
    {
      super(message.getBytes());
    }

    /**
     * Constructor from raw VE message
     *
     * @param messageBody - the VE message data to use
     */
    public VeDropMessageBuilder(byte[] messageBody)
    {
      super(messageBody);
    }

    /**
     * Creates instance of VE message
     *
     * @return SwitchBladeMessage
     */
    public VeDropMessage build()
    {
      boolean result = (messageBody != null);
      if (result && messageBody.length > 2)
      {
        int blockId = messageBody[BLOCK_ID_INDEX] & 0xFF;
        result =
            (blockId == DROP_TRACK_BLOCK && messageBody.length == MINIMUM_BYTES);
      }

      return result ? new VeDropMessage(this) : null;
    }
  }
}
