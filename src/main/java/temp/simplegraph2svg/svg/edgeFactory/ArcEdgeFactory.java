package temp.simplegraph2svg.svg.edgeFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.svg.SvgPoint;
import temp.simplegraph2svg.utils.SvgUtils;

import static temp.simplegraph2svg.svg.SvgElementsSizes.EDGE_OFFSET_RATIO;

public class ArcEdgeFactory implements EdgeFactory {

    @Override
    public Element create(Document doc, SvgPoint p1, SvgPoint p2, int index) {
        final Element result = doc.createElement("path");
        result.setAttribute("fill", "none");
        final SvgPoint c = getCenterWithOffset(p1, p2, index);
        StringBuilder sb = new StringBuilder()
                .append("M ").append(p1.x()).append(" ").append(p1.y()).append(" ")
                .append("Q ").append(c.x()).append(" ").append(c.y())
                .append(", ").append(p2.x()).append(" ").append(p2.y());
        result.setAttribute("d", sb.toString());
        return result;
    }

    private SvgPoint getCenterWithOffset(SvgPoint p1, SvgPoint p2, int index) {
        final double length = SvgUtils.length(p1, p2);
        final double sin = SvgUtils.sin(p1, p2);
        final int ax = (int) (-sin * EDGE_OFFSET_RATIO * length * (index + 1));
        final double cos = SvgUtils.cos(p1, p2);
        final int ay = (int) (cos * EDGE_OFFSET_RATIO * length * (index + 1));

        final SvgPoint center = SvgUtils.middle(p1, p2);
        return new SvgPoint(ax + center.x(), ay + center.y());
    }
}
