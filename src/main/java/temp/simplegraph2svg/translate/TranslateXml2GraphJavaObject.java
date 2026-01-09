package temp.simplegraph2svg.translate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import temp.simplegraph2svg.graph.Graph;
import temp.simplegraph2svg.graph.GraphEdge;
import temp.simplegraph2svg.graph.GraphNode;
import temp.simplegraph2svg.graph.GraphObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TranslateXml2GraphJavaObject
        implements Function<Document, Graph> {

    @Override
    public Graph apply(Document document) {
        final Element root = document.getDocumentElement();
        verify(root);
        return new Graph(getObjects(root));
    }

    private static void verify(Element root) {
        if (!root.getLocalName().equals("graph")) {
            throw new IllegalArgumentException("Invalid XML");
        }
    }

    private static List<GraphObject> getObjects(Element root) {
        final List<GraphObject> result = new ArrayList<>();
        Node node = root.getFirstChild();
        while (node != null) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                result.add(toObject((Element) node));
            }
            node = node.getNextSibling();
        }
        return result;
    }

    private static GraphObject toObject(Element root) {
        final String tag = root.getLocalName();
        return switch (tag) {
            case "node" -> toNode(root);
            case "edge" -> toEdge(root);
            default -> throw new IllegalArgumentException("Invalid tag name: " + tag);
        };
    }

    private static GraphNode toNode(Element root) {
        return new GraphNode(
                getId(root),
                getColor(root)
        );
    }

    private static GraphEdge toEdge(Element root) {
        return new GraphEdge(
                getId(root),
                getColor(root),
                root.getAttribute("sourceRef"),
                root.getAttribute("targetRef")
        );
    }

    private static String getId(Element elem) {
        return elem.getAttribute("id");
    }

    private static String getColor(Element elem) {
        return elem.getAttribute("color");
    }

}
