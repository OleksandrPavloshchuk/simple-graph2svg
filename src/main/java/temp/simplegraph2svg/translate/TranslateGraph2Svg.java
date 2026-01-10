package temp.simplegraph2svg.translate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import temp.simplegraph2svg.graph.*;
import temp.simplegraph2svg.svg.*;

import java.util.List;
import java.util.Map;

public record TranslateGraph2Svg(
        Document doc,
        Map<String, SvgPoint> coordinates,
        Graph graph) {

    public void convert() {
        final Element root = doc.getDocumentElement();
        graph.graphObjects().keySet().forEach(id ->
                convert(getGraphObject(id)).forEach(root::appendChild)
        );
    }

    private GraphObject getGraphObject(String id) {
        return graph.graphObjects().get(id);
    }

    private GraphNode getGraphNode(String id) {
        return (GraphNode) getGraphObject(id);
    }

    private List<Element> convert(GraphObject graphObject) {
        return switch (graphObject.type()) {
            case GraphObjectType.NODE -> convert((GraphNode) graphObject);
            case GraphObjectType.EDGE -> convert((GraphEdge) graphObject);
        };
    }

    private List<Element> convert(GraphEdge src) {
        final SvgPoint targetCenter = getCenter(getGraphNode(src.targetRef()));

        final SvgCircle sourceShape = getShape(src.sourceRef());
        final SvgPoint source = sourceShape.intersectLineFrom(targetCenter);

        final SvgShape targetShape = getShape(src.targetRef());
        final SvgPoint target = targetShape.intersectLineFrom(source);

        final List<GraphEdge> sameSourceAndTargetEdges = this.graph.getEdges(
                src.sourceRef(), src.targetRef()
        );
        final int index = findEdgeIndex(src.id(), sameSourceAndTargetEdges);

        final SvgObject edge = src.isSelf()
                ? new SvgSelfEdge(src.id(), src.color(), source, index)
                : new SvgEdge(src.id(), src.color(), source, target, index);
        final Element edgeElem = edge.createElement(doc);

        return List.of(edgeElem);
    }

    private SvgCircle getShape(String id) {
        final GraphNode graphNode = getGraphNode(id);
        return new SvgCircle(getCenter(graphNode), graphNode.color(), 1);
    }

    private List<Element> convert(GraphNode graphNode) {
        return List.of(
                convertToCircle(graphNode),
                convertToText(graphNode)
        );
    }

    private Element convertToCircle(GraphNode graphNode) {
        return new SvgCircle(
                getCenter(graphNode),
                graphNode.color(),
                1).createElement(doc);
    }

    private Element convertToText(GraphNode graphNode) {
        return new SvgText(getCenter(graphNode), graphNode.id()).createElement(doc);
    }

    private SvgPoint getCenter(GraphNode graphObject) {
        return coordinates.get(graphObject.id());
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
