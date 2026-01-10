package temp.simplegraph2svg.svg.edgeFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.svg.SvgPoint;
import temp.simplegraph2svg.utils.SvgUtils;

import static temp.simplegraph2svg.svg.SvgElementsSizes.EDGE_OFFSET_RATIO;
import static temp.simplegraph2svg.svg.SvgElementsSizes.SELF_EDGE_BASE_OFFSET;

public class SelfEdgeFactory implements EdgeFactory {

    private static final double RADIUS_FACTOR = Math.sqrt(2);

    @Override
    public Element create(Document doc, SvgPoint p1, SvgPoint p2, int index) {
        final Element result = doc.createElement("circle");
        result.setAttribute("fill", "none");
        final SvgPoint center = new SvgPoint(
                p1.x() - SELF_EDGE_BASE_OFFSET * (index+1),
                p1.y() + SELF_EDGE_BASE_OFFSET * (index+1)
        );
        result.setAttribute("cx", String.valueOf(center.x()));
        result.setAttribute("cy", String.valueOf(center.y()));
        result.setAttribute("r", String.valueOf(RADIUS_FACTOR * SELF_EDGE_BASE_OFFSET * (index+1)));
        return result;
    }

}
