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

public class CtcMessage
{
  public static final int TRACK_UPDATE_BLOCK = 0xC8;
  public static final int TRACKER_LOCATION_BLOCK = 0xC9;
  public static final int DROP_TRACK_BLOCK = 0xCA;
  public static final int CATI_BLOCK = 30;

  protected static final int SOURCE_ID_INDEX = 0;            // unsigned char
  protected static final int BLOCK_ID_INDEX = SOURCE_ID_INDEX + 1;    // unsigned char
  /**
   * Represents raw VE Message data
   * Specific frames have accessors to fields from this data
   */
  protected final byte[] messageBody;

  /**
   * Constructor of VeMessage
   *
   * @param builder - creates message
   */
  protected CtcMessage(CTCMessageBuilder builder)
  {
    messageBody = new byte[builder.messageBody.length];
    System.arraycopy(builder.messageBody, 0, messageBody, 0, builder.messageBody.length);
  }

  /**
   * Converts 2 bytes into int
   *
   * @param lsb - least significant byte
   * @param msb - most significant byte
   *
   * @return integer value
   */
  public static int convertTwoBytesToInt(byte lsb, byte msb)
  {
    return (msb << 8) | (lsb & 0xFF);
  }

  protected static long convertFourBytesToInt(byte b1, byte b2, byte b3, byte b4)
  {
    return (b4 << 24) | (b3 & 0xFF) << 16 | (b2 & 0xFF) << 8 | (b1 & 0xFF);
  }

  protected static long convertFourBytesToTime(byte b1, byte b2, byte b3, byte b4)
  {
    // Unpacks the timestamp which is seconds since midnight and comes in the order:
    // ( 1st byte MSW, 2nd byte MSW, 1st byte LSW, 2nd byte LSW
    // Multiple by 256 instead << 8
    Long retVal = (long) (b2 & 0x0FF);
    retVal = retVal * 256 + (long) (b1 & 0x0FF);
    retVal = retVal * 256 + (long) (b4 & 0x0FF);
    retVal = retVal * 256 + (long) (b3 & 0x0FF);
    return retVal;
  }

  public int getBlockId()
  {
    return messageBody[BLOCK_ID_INDEX];
  }

  public boolean isTrackUpdate()
  {
    return (messageBody[BLOCK_ID_INDEX] & 0xFF) == TRACK_UPDATE_BLOCK;
  }

  public boolean isTrackerLocation()
  {
    return (messageBody[BLOCK_ID_INDEX] & 0xFF) == TRACKER_LOCATION_BLOCK;
  }

  public boolean isTrackDrop()
  {
    return (messageBody[BLOCK_ID_INDEX] & 0xFF) == DROP_TRACK_BLOCK;
  }

  public boolean isBlock30()
  {
    return (messageBody[BLOCK_ID_INDEX] & 0xFF) == CATI_BLOCK;
  }

  /**
   * Gets the raw CTC data
   *
   * @return byte array
   */
  public byte[] getBytes()
  {
    byte[] result = new byte[messageBody.length];
    System.arraycopy(messageBody, 0, result, 0, messageBody.length);
    return result;
  }

  public static class CTCMessageBuilder
  {
    protected byte[] messageBody = null;

    /**
     * Constructor
     */
    public CTCMessageBuilder()
    {
    }

    /**
     * Constructor from raw VE message
     *
     * @param messageBody - the switch blade data to use
     */
    public CTCMessageBuilder(byte[] messageBody)
    {
      this.messageBody = new byte[messageBody.length];
      System.arraycopy(messageBody, 0, this.messageBody, 0, messageBody.length);
    }

    /**
     * Creates instance of VE message
     *
     * @return SwitchBladeMessage
     */
    public CtcMessage build()
    {
      boolean result = (messageBody != null);
      if (result && messageBody.length > 2)
      {
        int blockId = messageBody[BLOCK_ID_INDEX] & 0xFF;
        result =
            (blockId == TRACK_UPDATE_BLOCK) ||
                (blockId == TRACKER_LOCATION_BLOCK) ||
                (blockId == DROP_TRACK_BLOCK) ||
                (blockId == CATI_BLOCK);
      }

      return result ? new CtcMessage(this) : null;
    }
  }
}
