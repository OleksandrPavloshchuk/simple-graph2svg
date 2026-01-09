package temp.simplegraph2svg.translate;

import temp.simplegraph2svg.graph.Graph;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * This class distributes ids by rows and columns for further calculation of SVG positions
 */
public class DistributeNodes {
    private final Graph graph;
    private final String startId;

    private final Map<String, Position> positions = new TreeMap<>();

    private final Map<Integer, Integer> maxColByRow = new HashMap<>();

    public DistributeNodes(Graph graph, String startId) {
        this.graph = graph;
        this.startId = startId;
    }

    public Map<String, Position> getPositions() {
        return positions;
    }

    public DistributeNodes perform() {
        this.graph.findNodeById(this.startId).ifPresentOrElse(node -> {
                    visit(this.startId, 0);
                },
                () -> {
                    throw new IllegalArgumentException("Unexpected node id: " + this.startId);
                }
        );
        return this;
    }

    public int getMaxRow() {
        return getMax(Position::row);
    }

    public int getMaxCol() {
        return getMax(Position::col);
    }

    private void visit(String id, int row) {
        if (isVisited(id)) {
            return;
        }
        int col = maxColByRow.computeIfAbsent(row, k -> 0);
        positions.put(id, new Position(row, col));
        maxColByRow.put(row, col + 1);
        graph.getLinkedNodeIds(id).forEach(linkedNodeId -> visit(linkedNodeId, row + 1));
    }

    private boolean isVisited(String id) {
        return positions.containsKey(id);
    }

    private int getMax(Function<Position, Integer> getter) {
        return positions.values().stream()
                .map(getter)
                .max(Integer::compareTo)
                .orElse(0);
    }

    public record Position(int row, int col) {
    }

}
