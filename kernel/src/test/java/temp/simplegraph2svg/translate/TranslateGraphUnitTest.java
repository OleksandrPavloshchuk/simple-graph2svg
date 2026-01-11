package temp.simplegraph2svg.translate;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TranslateGraphUnitTest {

    @Test
    public void perform() throws ParserConfigurationException, IOException, SAXException {
        final String xml = "<graph>" +
                "<node id=\"1\" color=\"blue\" />" +
                "<node id=\"2\" color=\"blue\" />" +
                "<node id=\"3\" color=\"black\" />" +
                "<edge id=\"13\" color=\"black\" sourceRef=\"1\" targetRef=\"3\" />" +
                "<edge id=\"31\" color=\"black\" sourceRef=\"3\" targetRef=\"1\" />" +
                "<edge id=\"21\" color=\"black\" sourceRef=\"1\" targetRef=\"1\" />" +
                "</graph>";
        final InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new TranslateGraph(inputStream, outputStream).perform();
        Assert.assertEquals(outputStream.toString(), EXPECTED_SVG);
    }

    private static final String EXPECTED_SVG = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 1000 500\"><style type=\"text/css\">text {font-family: Arial, sans serif; font-size: 8pt; text-anchor:middle; dominant-baseline: middle;}</style><defs><marker id=\"arrow\" markerHeight=\"12\" markerWidth=\"12\" orient=\"auto-start-reverse\" refX=\"20\" refY=\"10\" viewBox=\"0 0 20 20\"><path d=\"M 0 0 L 20 10 L 0 20 Z\" fill=\"gray\"/></marker></defs><circle cx=\"300\" cy=\"100\" fill=\"blue\" r=\"15\" stroke=\"blue\" stroke-width=\"1\"/><text fill=\"black\" x=\"300\" y=\"100\">1</text><circle cx=\"500\" cy=\"100\" fill=\"blue\" r=\"15\" stroke=\"blue\" stroke-width=\"1\"/><text fill=\"black\" x=\"500\" y=\"100\">2</text><path d=\"M 310 110 Q 382 118, 390 190\" fill=\"none\" marker-end=\"url(#arrow)\" stroke=\"black\"/><circle cx=\"400\" cy=\"200\" fill=\"black\" r=\"15\" stroke=\"black\" stroke-width=\"1\"/><text fill=\"black\" x=\"400\" y=\"200\">3</text><path d=\"M 390 190 Q 318 182, 310 110\" fill=\"none\" marker-end=\"url(#arrow)\" stroke=\"black\"/><circle cx=\"280\" cy=\"120\" fill=\"none\" r=\"28.284271247461902\" stroke=\"black\"/></svg>";
}
