
package mil.don.common.conversions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlTranslator
{
  // static variable _instance of type Singleton
  private static XmlTranslator _instance = null;

  // contexts are expensive to create, so we want to save one off per type
  private static HashMap<Class<?>,JAXBContext> _contextMap;

  private XmlTranslator()
  {
    _contextMap = new HashMap<>();
  }

  public static XmlTranslator getInstance()
  {
    if ( _instance == null )
    {
      _instance = new XmlTranslator();
    }

    return _instance;
  }

  /**
   * The encoder of objects
   * @param classType Type of the object to translate
   * @param data the object to translate
   * @return The byte array requested (Can return null)
   */
  public byte[] encode(Class<?> classType, Object data)
  {
    byte[] result = null;

    try
    {
      JAXBContext context = _contextMap.get(classType);
      if( context == null )
      {
        context = JAXBContext.newInstance(classType);
        _contextMap.put(classType,context);
      }

      Marshaller marshaller = context.createMarshaller();
      StringWriter writer = new StringWriter();
      marshaller.marshal(data, writer);
      result = writer.toString().getBytes();
    }
    catch ( JAXBException e )
    {
      //IDonLogging logger = LoggingFactory.getLogger(ObjectTranslator.class);
      System.out.println("Failed to Translate to a byte array: " + e.getMessage());
    }

    return result;
  }

  /**
   * The decoder of byte[]
   * @param classType Type of the object to translate
   * @param data the byte[] to translate
   * @return The byte array requested (Can return null)
   */
  public <T> T decode(Class<?> classType, byte[] data)
  {
    T result = null;
    try
    {
      JAXBContext context = _contextMap.get(classType);
      if(context==null)
      {
        context = JAXBContext.newInstance(classType);
        _contextMap.put(classType,context);
      }

      Unmarshaller unmarshaller = context.createUnmarshaller();
      InputStream stream = new ByteArrayInputStream(data);

      // Create the builder
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(true);
      factory.setExpandEntityReferences(false);
      factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
      factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
      factory.setFeature("http://apache.org/xml/features/validation/dynamic", true);
      DocumentBuilder builder = factory.newDocumentBuilder();

      result = (T)unmarshaller.unmarshal(builder.parse(stream));
    }
    catch ( Exception e )
    {
      //IDonLogging logger = LoggingFactory.getLogger(ObjectTranslator.class);
      System.out.println("ERROR in decoding message to '" + classType + "' : " + e.getMessage());
    }

    return result;
  }

}
