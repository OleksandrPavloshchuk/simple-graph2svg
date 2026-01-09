package temp.simplegraph2svg.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static temp.simplegraph2svg.svg.SvgElementsSizes.RADIUS;

public record SvgCircle(
        SvgPoint center,
        String color,
        int borderWidth
) implements SvgShape {

    @Override
    public SvgPoint intersectLineFrom(SvgPoint start) {
        final double dx = center.x() - start.x();
        final double dy = center.y() - start.y();

        final double length = Math.sqrt(dx * dx + dy * dy);

        final double cos = dx / length;
        final double sin = dy / length;

        final double r = RADIUS + borderWidth / 2.0;

        return new SvgPoint(
                center.x() - (int) (cos * r),
                center.y() - (int) (sin * r)
        );
    }

    @Override
    public Element createElement(Document doc) {
        final Element result = doc.createElement("circle");
        result.setAttribute("cx", String.valueOf(center.x()));
        result.setAttribute("cy", String.valueOf(center.y()));
        result.setAttribute("r", String.valueOf(RADIUS));
        result.setAttribute("stroke", color);
        result.setAttribute("fill", color);
        result.setAttribute( "stroke-width", String.valueOf(borderWidth));
        return result;
    }
}
