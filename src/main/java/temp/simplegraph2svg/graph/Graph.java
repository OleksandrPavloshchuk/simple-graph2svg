package temp.simplegraph2svg.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record Graph(
        Map<String, GraphObject> graphObjects
) {

    public Set<String> getNodeIds() {
        return graphObjects.values().stream()
                .filter(obj -> obj.type() == GraphObjectType.NODE)
                .map(GraphObject::id)
                .collect(Collectors.toSet());
    }

    public List<String> getLinkedNodeIds(String id) {
        return graphObjects.values().stream()
                .filter(obj -> obj.type() == GraphObjectType.EDGE)
                .map(GraphEdge.class::cast)
                .filter(link -> link.sourceRef().equals(id))
                .map(GraphEdge::targetRef)
                .toList();
    }

    public List<GraphEdge> getEdges(String sourceRef, String targetRef) {
        return graphObjects.values().stream()
                .filter(obj -> obj.type() == GraphObjectType.EDGE)
                .map(GraphEdge.class::cast)
                .filter( edge -> edge.sourceRef().equals(sourceRef))
                .filter( edge -> edge.targetRef().equals(targetRef))
                .toList();
    }
}
