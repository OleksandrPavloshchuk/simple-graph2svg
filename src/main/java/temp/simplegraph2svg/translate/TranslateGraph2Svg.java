package temp.simplegraph2svg.translate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.graph.*;
import temp.simplegraph2svg.svg.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record TranslateGraph2Svg(
        Document doc,
        Map<String, SvgPoint> coordinates,
        Graph graph) {

    public void convert() {
        final Element root = doc.getDocumentElement();
        final Set<String> ids = graph.graphObjects().keySet();
        ids.forEach(id -> {
            final List<Element> elements = convert(getGraphObject(id));
            elements.forEach(root::appendChild);
        });
    }

    private GraphObject getGraphObject(String id) {
        return graph.graphObjects().get(id);
    }

    private List<Element> convert(GraphObject graphObject) {
        return switch (graphObject.type()) {
            case GraphObjectType.NODE -> convert((GraphNode) graphObject);
            case GraphObjectType.EDGE -> convert((GraphEdge) graphObject);
        };
    }

    private List<Element> convert(GraphEdge src) {
        final SvgPoint targetCenter = getCenter(getGraphObject(src.targetRef()));

        final SvgCircle sourceShape = getShape(src.sourceRef());
        final SvgPoint source = sourceShape.intersectLineFrom(targetCenter);

        final SvgShape targetShape = getShape(src.targetRef());
        final SvgPoint target = targetShape.intersectLineFrom(source);

        final List<GraphEdge> sameSourceAndTargetEdges = this.graph.getEdges(
                src.sourceRef(), src.targetRef()
        );
        final int index = findEdgeIndex(src.id(),sameSourceAndTargetEdges);

        final SvgObject edge = src.isSelf()
                ? new SvgSelfEdge(src.id(), src.color(), source, index)
                : new SvgEdge(src.id(), src.color(), source, target, index);
        final Element edgeElem = edge.createElement(doc);

        return List.of(edgeElem);
    }

    private SvgCircle getShape(String id) {
        final GraphObject graphObject = getGraphObject(id);
        final SvgPoint center = getCenter(graphObject);
        return new SvgCircle(center, graphObject.color(), 1);
    }

    private List<Element> convert(GraphNode graphNode) {
        return List.of(
                convertToCircle(graphNode),
                convertToText(graphNode)
        );
    }

    private Element convertToCircle(GraphObject graphObject) {
        return new SvgCircle(
                getCenter(graphObject),
                graphObject.color(),
                1).createElement(doc);
    }

    private Element convertToText(GraphObject graphObject) {
        return new SvgText(getCenter(graphObject), graphObject.id()).createElement(doc);
    }

    private SvgPoint getCenter(GraphObject graphObject) {
        return switch (graphObject.type()) {
            case GraphObjectType.EDGE -> throw new IllegalArgumentException("Unexpected type: " + graphObject.type());
            case GraphObjectType.NODE -> coordinates.get(graphObject.id());
        };
    }

    private static int findEdgeIndex(String edgeId, List<GraphEdge> edges) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).id().equals(edgeId)) {
                return i;
            }
        }
        return -1;
    }

}
