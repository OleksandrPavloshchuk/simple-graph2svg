package temp.simplegraph2svg.svg.edgeFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.svg.SvgPoint;

public interface EdgeFactory {
    Element create(Document doc, SvgPoint p1, SvgPoint p2);
}
