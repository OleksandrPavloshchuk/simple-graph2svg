package temp.simplegraph2svg.graph;

public record GraphEdge(
        String id,
        String color,
        String sourceRef,
        String targetRef
) implements GraphObject {

    @Override
    public GraphObjectType type() {
        return GraphObjectType.EDGE;
    }
}
