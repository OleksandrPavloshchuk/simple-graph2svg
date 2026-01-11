package temp.simplegraph2svg.svg.edgeFactory;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.svg.SvgPoint;
import temp.simplegraph2svg.utils.XmlUtils;

import javax.xml.parsers.ParserConfigurationException;

public class ArcEdgeFactoryUnitTest {

    @Test
    public void create() throws ParserConfigurationException {
        final Document doc = XmlUtils.getDocumentBuilder().newDocument();
        final Element actual = new ArcEdgeFactory().create(doc,
                new SvgPoint(100, 50),
                new SvgPoint( 300, 500),
                3);
        Assert.assertEquals(actual.getTagName(), "path");
        Assert.assertEquals(actual.getAttribute("fill"), "none");
        Assert.assertEquals(actual.getAttribute("d"), "M 100 50 Q 920 -45, 300 500");
    }
}
