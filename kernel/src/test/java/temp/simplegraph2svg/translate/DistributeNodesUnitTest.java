package temp.simplegraph2svg.translate;

import org.testng.Assert;
import org.testng.annotations.Test;
import temp.simplegraph2svg.graph.Graph;
import temp.simplegraph2svg.graph.GraphEdge;
import temp.simplegraph2svg.graph.GraphNode;
import temp.simplegraph2svg.graph.GraphObject;

import java.util.HashMap;
import java.util.Map;

public class DistributeNodesUnitTest {

    @Test
    public void perform() {
        final DistributeNodes distributeNodes = new DistributeNodes(getGraph()).perform();
        Assert.assertEquals( distributeNodes.getMaxCol(), 1);
        Assert.assertEquals( distributeNodes.getMaxRow(), 2);
        Assert.assertEquals( distributeNodes.getPositions(), Map.of(
                "1", new DistributeNodes.Position(0,0),
                "2", new DistributeNodes.Position(1,0),
                "3", new DistributeNodes.Position(1,1),
                "4", new DistributeNodes.Position(0,1),
                "5", new DistributeNodes.Position(2,0)
        ));

    }

    private static Graph getGraph() {
        Map<String, GraphObject> map = new HashMap<>();
        map.put("1", new GraphNode("1", ""));
        map.put("2", new GraphNode("2", ""));
        map.put("3", new GraphNode("3", ""));
        map.put("4", new GraphNode("4", ""));
        map.put("5", new GraphNode("5", ""));
        map.put("1-2", new GraphEdge("1-2", "", "1", "2"));
        map.put("2-5", new GraphEdge("2-5", "", "2", "5"));
        map.put("1-3", new GraphEdge("1-3", "", "1", "3"));
        final Graph graph = new Graph(map);
        return graph;
    }
}
