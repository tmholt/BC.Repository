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

package mil.don.common.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtilities
{

  /**
   * Decompress a GZipped byte array
   *
   * @param data data stream
   *
   * @return retVal decompressed data stream
   */
  public static byte[] decompress(byte[] data)
      throws IOException
  {
    byte[] retVal = data;

    // Check if the input data is gzipped
    if (isGZipped(data))
    {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      GZIPInputStream gis = new GZIPInputStream(bis);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      // For safety, we assume a 100% compression rate
      byte[] outputBuffer = new byte[data.length * 2];

      int sizeRead;
      while ((sizeRead = gis.read(outputBuffer)) != -1)
      {
        outputStream.write(outputBuffer, 0, sizeRead);
      }

      outputStream.close();
      gis.close();
      bis.close();

      retVal = outputStream.toByteArray();
    }

    return retVal;
  }

  /**
   * Compress string input into a GZip byte array
   *
   * @param data string to compress
   *
   * @return retVal Compressed input data
   */
  public static byte[] compress(String data)
      throws IOException
  {
    return compress(data.getBytes());
  }

  /**
   * Compress input into a GZip byte array
   *
   * @param data Data to compress
   *
   * @return retVal Compressed input data
   */
  public static byte[] compress(byte[] data)
      throws IOException
  {
    byte[] retVal;

    // Check if data is already compressed for some bizarre reason
    if (isGZipped(data))
    {
      retVal = data;
    }
    // If the data is not compressed, time to compress it
    else
    {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      GZIPOutputStream gos = new GZIPOutputStream(bos);

      // Compressed data in the worst case scenario, will have a 0% compression rate
      byte[] outputBuffer = new byte[data.length];

      int sizeRead;
      while ((sizeRead = bis.read(outputBuffer)) != -1)
      {
        gos.write(outputBuffer, 0, sizeRead);
      }

      gos.close();
      bos.close();
      bis.close();

      retVal = bos.toByteArray();
    }

    return retVal;
  }

  /**
   * Check to see if a byte array is gzipped
   *
   * @param data byte array of data
   *
   * @return retVal is the data gzipped
   */
  private static boolean isGZipped(byte[] data)
  {
    return (data != null)
        && (data.length > 2)
        && ((data[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
        && (data[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8)));
  }
}
