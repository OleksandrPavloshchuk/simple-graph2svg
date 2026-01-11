package temp.simplegraph2svg.graph;

public record GraphNode(
        String id,
        String color
) implements GraphObject {

    @Override
    public GraphObjectType type() {
        return GraphObjectType.NODE;
    }
}
