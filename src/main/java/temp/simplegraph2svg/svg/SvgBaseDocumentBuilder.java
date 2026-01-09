package temp.simplegraph2svg.svg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.utils.XmlUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public record SvgBaseDocumentBuilder(
        int maxCol,
        int maxRow
) {
    private static final Logger LOGGER = Logger.getLogger(SvgBaseDocumentBuilder.class.getName());

    private static final String SVG_STYLE =
            "text {font-family: Arial, sans serif; font-size: 8pt; text-anchor:middle; dominant-baseline: middle;}";

    public Optional<Document> build() {
        try {
            final int width = (maxCol + 2) * SvgElementsSizes.X_STEP;
            final int height = (maxRow + 2) * SvgElementsSizes.Y_STEP;
            final Document doc = XmlUtils.getDocumentBuilder().newDocument();
            doc.appendChild(createSvgElement(doc, width, height));
            return Optional.of(doc);
        } catch( ParserConfigurationException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            return Optional.empty();
        }
    }

    private static Element createSvgElement(Document doc, int width, int height) {
        final Element root = doc.createElement("svg");
        root.setAttribute("viewBox", "0 0 " + width + " " + height);
        root.setAttribute("xmlns", "http://www.w3.org/2000/svg");
        root.appendChild(createStyleElement(doc));
        root.appendChild(createDefsElement(doc));
        return root;
    }

    private static Element createDefsElement(Document doc) {
        final Element elemDefs =  doc.createElement("defs");
        elemDefs.appendChild(createMarkerElement(doc));
        return elemDefs;
    }

    private static Element createMarkerElement(Document doc) {
        final Element elemMarker = doc.createElement("marker");
        elemMarker.setAttribute("id", "arrow");
        elemMarker.setAttribute("viewBox", "0 0 20 20");
        elemMarker.setAttribute("refX", "20");
        elemMarker.setAttribute("refY", "10");
        elemMarker.setAttribute("markerWidth", "12");
        elemMarker.setAttribute("markerHeight","12");
        elemMarker.setAttribute("orient","auto-start-reverse");
        elemMarker.appendChild(createPathElem(doc));
        return elemMarker;
    }

    private static Element createPathElem(Document doc) {
        final Element result = doc.createElement("path");
        result.setAttribute("fill", "gray");
        result.setAttribute("d", "M 0 0 L 20 10 L 0 20 Z");
        return result;
    }

    private static Element createStyleElement(Document doc) {
        final Element elemStyle = doc.createElement("style");
        elemStyle.setAttribute("type", "text/css");
        elemStyle.appendChild( doc.createTextNode(SVG_STYLE));
        return elemStyle;
    }

}
