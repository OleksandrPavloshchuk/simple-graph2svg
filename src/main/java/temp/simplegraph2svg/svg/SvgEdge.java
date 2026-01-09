package temp.simplegraph2svg.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.utils.SvgUtils;

import static temp.simplegraph2svg.svg.SvgElementsSizes.EDGE_OFFSET;

public record SvgEdge(
        String id,
        String color,
        SvgPoint source,
        SvgPoint target
) implements SvgObject {

    @Override
    public SvgPoint center() {
        return new SvgPoint(
                (source.x() + target.x()) / 2,
                (source.y() + target.y()) / 2
        );
    }

    @Override
    public Element createElement(Document doc) {
        final Element result = doc.createElement("path");
        result.setAttribute("stroke", color);
        result.setAttribute("fill", "none");
        result.setAttribute("marker-end", "url(#arrow)");

        final SvgPoint c = getCenterWithOffset();
        StringBuilder sb = new StringBuilder()
                .append("M ").append(source.x()).append(" ").append(source.y()).append(" ")
                .append("Q ").append(c.x()).append(" ").append(c.y())
                .append(", ").append(target.x()).append(" ").append(target.y());


        result.setAttribute("d",sb.toString());
        return result;
    }

    private SvgPoint getCenterWithOffset() {
        final SvgPoint center = center();
        final double cos = SvgUtils.cos(source, target);
        final double sin = SvgUtils.sin(source, target);
        final int ax = (int) (sin * EDGE_OFFSET);
        final int ay = (int) (cos * EDGE_OFFSET);
        return new SvgPoint(ax + center.x(), ay +  center.y());
    }


}
