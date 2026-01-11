package temp.simplegraph2svg.graph;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class GraphUnitTest {

    @Test
    public void getNodeIds() {
        final Map<String, GraphObject> src = new HashMap<>();
        src.put("id-1", new GraphNode("id-1", "color-1"));
        src.put("id-2", new GraphNode("id-2", "color-2"));
        src.put("id-2-id-1", new GraphEdge("id-2-id-1", "color-2", "id-2", "id-1"));

        final Set<String> actual = new Graph(src).getNodeIds();
        Assert.assertEquals(actual, Set.of("id-2", "id-1"));
    }

    @Test
    public void getLinkedNodeIds() {
        final Map<String, GraphObject> src = new HashMap<>();
        src.put("1", new GraphNode("1", ""));
        src.put("2", new GraphNode("2", ""));
        src.put("1-2", new GraphEdge("1-2", "", "1", "2"));
        src.put("3", new GraphNode("3", ""));
        src.put("4", new GraphNode("4", ""));
        src.put("2-3", new GraphEdge("2-3", "", "2", "3"));
        src.put("1-4", new GraphEdge("1-4", "", "1", "4"));

        final List<String> actual = new Graph(src).getLinkedNodeIds("1");
        Assert.assertEquals(actual, List.of("2", "4"));
    }

    @Test
    public void getEdges() {
        final Map<String, GraphObject> src = new HashMap<>();
        src.put("1", new GraphNode("1", ""));
        src.put("2", new GraphNode("2", ""));
        src.put("1-2", new GraphEdge("1-2", "", "1", "2"));
        src.put("3", new GraphNode("3", ""));
        src.put("4", new GraphNode("4", ""));
        src.put("2-3", new GraphEdge("2-3", "", "2", "3"));
        src.put("1-4", new GraphEdge("1-4", "", "1", "4"));

        final List<GraphEdge> actual = new Graph(src).getEdges("2", "3");
        Assert.assertEquals(actual, List.of(
                new GraphEdge("2-3", "", "2", "3")
        ));
    }
}
