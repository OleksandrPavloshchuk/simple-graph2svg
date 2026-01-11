package temp.simplegraph2svg.utils;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlUtils {
    private static final Logger LOGGER = Logger.getLogger(XmlUtils.class.getName());

    private XmlUtils() {
    }

    public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder();
    }

    public static void writeDocumentToOutput(Document src, OutputStream output) {
        try {
            TransformerFactory.newInstance()
                    .newTransformer()
                    .transform(new DOMSource(src), new StreamResult(output));
        } catch (TransformerException ex) {
            LOGGER.log(Level.SEVERE, "Writing error", ex);
        }
    }
}
