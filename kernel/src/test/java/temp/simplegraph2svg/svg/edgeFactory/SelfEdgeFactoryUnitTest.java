package temp.simplegraph2svg.svg.edgeFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.svg.SvgPoint;
import temp.simplegraph2svg.utils.XmlUtils;

import javax.xml.parsers.ParserConfigurationException;

public class SelfEdgeFactoryUnitTest {

    @Test
    public void create() throws ParserConfigurationException {
        final Document doc = XmlUtils.getDocumentBuilder().newDocument();
        final Element actual = new SelfEdgeFactory().create(doc,
                new SvgPoint(180, 150),
                new SvgPoint( 180, 150),
                2);
        Assert.assertEquals(actual.getTagName(), "circle");
        Assert.assertEquals(actual.getAttribute("fill"), "none");
        Assert.assertEquals(actual.getAttribute("cx"), "120");
        Assert.assertEquals(actual.getAttribute("cy"), "210");
        Assert.assertEquals(actual.getAttribute("r"), "84.8528137423857");
    }
}
