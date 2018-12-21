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

public class VeMessage  extends CtcMessage
{

  protected static final double VELOCITY_SCALE = 0.125; // 0.125 meter/second
  protected static final double ECEF_SCALE = 1;      // 1 meter
  protected static final double TIME_SCALE = 10000;    // 100us


  protected VeMessage(VeMessageBuilder builder)
  {
    super(builder);
  }

  public int getRemoteSensor()
  {

    return messageBody[SOURCE_ID_INDEX];
  }


  public static class VeMessageBuilder extends CTCMessageBuilder
  {
    /**
     * Constructor
     */
    public VeMessageBuilder()
    {
    }

    /**
     * Constructor from raw VE message
     *
     * @param message - the VE message data to use
     */
    public VeMessageBuilder(CtcMessage message)
    {
      super(message.getBytes());
    }

    /**
     * Constructor from raw VE message
     *
     * @param messageBody - the VE message data to use
     */
    public VeMessageBuilder(byte[] messageBody)
    {
      super(messageBody);
    }

    /**
     * Creates instance of VE message
     *
     * @return SwitchBladeMessage
     */
    public VeMessage build()
    {
      boolean result = (messageBody != null);
      if (result && messageBody.length > 2)
      {
        int blockId = messageBody[BLOCK_ID_INDEX] & 0xFF;
        result =
            (blockId == TRACK_UPDATE_BLOCK) ||
                (blockId == TRACKER_LOCATION_BLOCK) ||
                (blockId == DROP_TRACK_BLOCK);
      }

      return result ? new VeMessage(this) : null;
    }
  }
}
