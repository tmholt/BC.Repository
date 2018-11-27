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

package mil.don.common.conversions;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mil.don.common.messages.tcut21.EWMessage;

public class ByteToTcut21EwDecoder
{
    public EWMessage decode(byte[] data) {
        EWMessage result;
        try
        {
            JAXBContext context = JAXBContext.newInstance(EWMessage.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            InputStream stream = new ByteArrayInputStream(trim(data));

            // Create the builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setExpandEntityReferences(false);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            factory.setFeature("http://apache.org/xml/features/validation/dynamic", true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            result = (EWMessage) unmarshaller.unmarshal(builder.parse(stream));
        }
        catch ( SAXException | IOException | JAXBException | ParserConfigurationException e)
        {
            throw new IllegalArgumentException("Could not unmarshall message: " + e.getMessage());
        }

        return result;
    }

    // trim trailing 0s from a byte array, returning a new array.
    private static byte[] trim(byte[] bytes)
    {
        int length = bytes.length - 1;
        int i = length;
        while ( i >= 0 && bytes[i] == 0 )
        {
            i--;
        }

        if ( i == length ) {
            return bytes; // no change
        }
        else
        {
            return Arrays.copyOf(bytes, i + 1);
        }
    }
}
