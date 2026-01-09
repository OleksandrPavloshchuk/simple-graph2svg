package temp.simplegraph2svg.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public record SvgText(
        SvgPoint center,
        String text
) implements SvgObject {

    @Override
    public Element createElement(Document doc) {
        final Element result = doc.createElement("text");
        result.setAttribute("fill", "black");
        result.setAttribute("x", String.valueOf(center.x()));
        result.setAttribute("y", String.valueOf(center.y()));
        result.appendChild(doc.createTextNode(text));
        return result;
    }
}
