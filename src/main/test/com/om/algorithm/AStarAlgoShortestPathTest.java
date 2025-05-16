package com.om.algorithm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class AStarAlgoShortestPathTest {

    private void setupGraph(IAlgoShortestPath algo) {
        algo.addNode("A", 0, 0);
        algo.addNode("B", 1, 0);
        algo.addNode("C", 1, 1);
        algo.addNode("D", 0, 1);

        algo.addEdge("A", "B", 1);
        algo.addEdge("B", "C", 2);
        algo.addEdge("A", "D", 4);
        algo.addEdge("D", "C", 1);
    }

    @Test
    public void testAStarShortestPathCorrect() {
        IAlgoShortestPath algo = new AStarAlgoShortestPathImpl();
        setupGraph(algo);
        PathResult result = algo.findShortestPath("A", "C");

        assertEquals(3.0, result.getTotalWeight(), 0.01);
        assertEquals(List.of("A", "B", "C"), result.getPath());
    }

    @Test
    public void testAStarNoPath() {
        IAlgoShortestPath algo = new AStarAlgoShortestPathImpl();
        setupGraph(algo);
        algo.removeEdge("A", "B");
        algo.removeEdge("A", "D");

        PathResult result = algo.findShortestPath("A", "C");

        assertEquals(Double.POSITIVE_INFINITY, result.getTotalWeight());
        assertTrue(result.getPath().isEmpty());
    }

    @Test
    public void testNonExistingNode() {
        IAlgoShortestPath algo = new AStarAlgoShortestPathImpl();
        setupGraph(algo);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            algo.findShortestPath("A", "Z"); // Z does not exist
        });

        assertTrue(exception.getMessage().contains("Z"));
    }
} 