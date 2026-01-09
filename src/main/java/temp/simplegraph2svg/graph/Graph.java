package temp.simplegraph2svg.graph;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record Graph(
        Map<String, GraphObject> graphObjects
) {

    public Set<String> getLinkedNodeIds(String id) {
        return graphObjects.values().stream()
                .filter( obj -> obj.type()==GraphObjectType.EDGE)
                .map(GraphEdge.class::cast)
                .filter( link -> link.sourceRef().equals(id))
                .map(GraphEdge::targetRef)
                .collect(Collectors.toSet());
    }
}
