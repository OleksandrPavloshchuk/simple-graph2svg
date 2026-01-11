package temp.simplegraph2svg.translate;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import temp.simplegraph2svg.graph.Graph;
import temp.simplegraph2svg.graph.GraphEdge;
import temp.simplegraph2svg.graph.GraphNode;
import temp.simplegraph2svg.utils.XmlUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class TranslateXml2GraphJavaObjectUnitTest {

    @Test
    public void apply() throws ParserConfigurationException, IOException, SAXException {
        final String xml = "<graph>" +
                "<node id=\"1\" color=\"red\" />" +
                "<node id=\"2\" color=\"red\" />" +
                "<node id=\"3\" color=\"red\" />" +
                "<edge id=\"12\" color=\"black\" sourceRef=\"1\" targetRef=\"2\" />" +
                "<edge id=\"23\" color=\"black\" sourceRef=\"2\" targetRef=\"3\" />" +
                "<edge id=\"31\" color=\"black\" sourceRef=\"3\" targetRef=\"1\" />" +
                "</graph>";

        final Document doc = XmlUtils.getDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
        final Graph actual = new TranslateXml2GraphJavaObject().apply(doc);

        Assert.assertEquals(actual.graphObjects().keySet(), Set.of("1", "12", "23", "2", "3", "31"));
        Assert.assertEquals(actual.graphObjects().get("1"), new GraphNode("1", "red"));
        Assert.assertEquals(actual.graphObjects().get("2"), new GraphNode("2", "red"));
        Assert.assertEquals(actual.graphObjects().get("3"), new GraphNode("3", "red"));
        Assert.assertEquals(actual.graphObjects().get("12"), new GraphEdge("12", "black", "1", "2"));
        Assert.assertEquals(actual.graphObjects().get("23"), new GraphEdge("23", "black", "2", "3"));
        Assert.assertEquals(actual.graphObjects().get("31"), new GraphEdge("31", "black", "3", "1"));
    }
}
