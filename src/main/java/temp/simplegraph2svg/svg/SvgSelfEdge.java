package temp.simplegraph2svg.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.svg.edgeFactory.SelfEdgeFactory;

public record SvgSelfEdge(
        String id,
        String color,
        SvgPoint center,
        int index
) implements SvgObject {

    @Override
    public Element createElement(Document doc) {
        final Element result = new SelfEdgeFactory().create(doc, center, center, index);
        result.setAttribute("stroke", color);
        return result;
    }

}
