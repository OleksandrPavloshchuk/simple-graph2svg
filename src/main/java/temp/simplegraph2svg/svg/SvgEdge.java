package temp.simplegraph2svg.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.svg.edgeFactory.ArcEdgeFactory;
import temp.simplegraph2svg.utils.SvgUtils;

public record SvgEdge(
        String id,
        String color,
        SvgPoint source,
        SvgPoint target
) implements SvgObject {

    @Override
    public SvgPoint center() {
        return SvgUtils.middle(source, target);
    }

    @Override
    public Element createElement(Document doc) {
        final Element result = new ArcEdgeFactory().create(doc, source, target);
        result.setAttribute("stroke", color);
        result.setAttribute("marker-end", "url(#arrow)");
        return result;
    }

}
