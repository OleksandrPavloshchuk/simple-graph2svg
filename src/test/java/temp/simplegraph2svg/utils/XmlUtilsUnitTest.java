package temp.simplegraph2svg.utils;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class XmlUtilsUnitTest {

    @Test
    public void getDocumentBuilder() throws ParserConfigurationException {
        Assert.assertNotNull(XmlUtils.getDocumentBuilder());
    }

    @Test
    public void writeDocumentToOutputOk() throws ParserConfigurationException, IOException, SAXException {
        final String src = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root><item id=\"1\"/></root>";
        final Document doc = XmlUtils.getDocumentBuilder().parse(
                new ByteArrayInputStream(src.getBytes())
        );
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        XmlUtils.writeDocumentToOutput(doc, output);
        final String actual = output.toString();
        Assert.assertEquals(actual, src);
    }

    @Test
    public void writeDocumentToOutputError() throws ParserConfigurationException, IOException, SAXException {
        final String src = "<a/>";
        final Document doc = XmlUtils.getDocumentBuilder().parse(
                new ByteArrayInputStream(src.getBytes())
        );
        final OutputStream  output = new OutputStream() {
            @Override
            public void write(int i) throws IOException {
                throw new IOException("always error");
            }
        };
        XmlUtils.writeDocumentToOutput(doc, output);
    }

}
