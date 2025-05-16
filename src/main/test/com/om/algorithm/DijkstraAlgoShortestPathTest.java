package com.om.algorithm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class DijkstraAlgoShortestPathTest {

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
    public void testDijkstraShortestPathCorrect() {
        IAlgoShortestPath algo = new DijkstraAlgoShortestPathImpl();
        setupGraph(algo);
        PathResult result = algo.findShortestPath("A", "C");

        assertEquals(3.0, result.getTotalWeight(), 0.01);
        assertEquals(List.of("A", "B", "C"), result.getPath());
    }

    @Test
    public void testDijkstraNoPath() {
        IAlgoShortestPath algo = new DijkstraAlgoShortestPathImpl();
        setupGraph(algo);
        algo.removeEdge("A", "B");
        algo.removeEdge("A", "D");

        PathResult result = algo.findShortestPath("A", "C");

        assertEquals(Double.POSITIVE_INFINITY, result.getTotalWeight());
        assertTrue(result.getPath().isEmpty());
    }

    @Test
    public void testSameStartAndEnd() {
        IAlgoShortestPath algo = new DijkstraAlgoShortestPathImpl();
        setupGraph(algo);

        PathResult result = algo.findShortestPath("A", "A");

        assertEquals(0.0, result.getTotalWeight(), 0.01);
        assertEquals(List.of("A"), result.getPath());
    }

    @Test
    public void testDisconnectedGraph() {
        IAlgoShortestPath algo = new DijkstraAlgoShortestPathImpl();

        // Component 1
        algo.addNode("A", 0, 0);
        algo.addNode("B", 1, 0);
        algo.addEdge("A", "B", 1);

        // Component 2
        algo.addNode("X", 10, 10);
        algo.addNode("Y", 11, 10);
        algo.addEdge("X", "Y", 1);

        PathResult result = algo.findShortestPath("A", "Y");

        assertEquals(Double.POSITIVE_INFINITY, result.getTotalWeight());
        assertTrue(result.getPath().isEmpty());
    }

    @Test
    public void testMultipleEqualPaths() {
        IAlgoShortestPath algo = new DijkstraAlgoShortestPathImpl();

        algo.addNode("A", 0, 0);
        algo.addNode("B", 1, 0);
        algo.addNode("C", 0, 1);
        algo.addNode("D", 1, 1);

        algo.addEdge("A", "B", 1);
        algo.addEdge("B", "D", 1);
        algo.addEdge("A", "C", 1);
        algo.addEdge("C", "D", 1);

        PathResult result = algo.findShortestPath("A", "D");

        assertEquals(2.0, result.getTotalWeight(), 0.01);
        assertTrue(
                result.getPath().equals(List.of("A", "B", "D")) ||
                        result.getPath().equals(List.of("A", "C", "D"))
        );
    }

    @Test
    public void testEmptyGraph() {
        IAlgoShortestPath algo = new DijkstraAlgoShortestPathImpl();

        assertThrows(IllegalArgumentException.class, () -> {
            algo.findShortestPath("A", "B");
        });
    }
} 