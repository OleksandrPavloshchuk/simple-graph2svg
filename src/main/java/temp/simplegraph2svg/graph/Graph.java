package temp.simplegraph2svg.graph;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record Graph(
        List<GraphObject> graphObjects
) {

    public Set<String> getNodeIds() {
        return graphObjects.stream()
                .filter( graphObject -> graphObject.type().equals(GraphObjectType.NODE))
                .map(GraphObject::id)
                .collect(Collectors.toSet());
    }

    public Optional<GraphNode> findNodeById(String id) {
        return graphObjects.stream()
                .filter( graphObject -> graphObject.type().equals(GraphObjectType.NODE))
                .map(GraphNode.class::cast)
                .filter( graphNode -> graphNode.id().equals(id))
                .findFirst();
    }

    public Set<String> getLinkedNodeIds(String id) {
        return graphObjects.stream()
                .filter( obj -> obj.type()==GraphObjectType.EDGE)
                .map(GraphEdge.class::cast)
                .filter( link -> link.sourceRef().equals(id))
                .map(GraphEdge::targetRef)
                .collect(Collectors.toSet());
    }
}
