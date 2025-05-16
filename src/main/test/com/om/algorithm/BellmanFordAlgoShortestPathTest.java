package com.om.algorithm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class BellmanFordAlgoShortestPathTest {

    @Test
    public void testBasicShortestPath() {
        IAlgoShortestPath algo = new BellmanFordAlgoShortestPathImpl();
        
        algo.addNode("A", 0, 0);
        algo.addNode("B", 1, 0);
        algo.addNode("C", 1, 1);
        
        algo.addEdge("A", "B", 4);
        algo.addEdge("B", "C", 3);
        algo.addEdge("A", "C", 8);
        
        PathResult result = algo.findShortestPath("A", "C");
        
        assertEquals(7.0, result.getTotalWeight(), 0.01);
        assertEquals(List.of("A", "B", "C"), result.getPath());
    }

    @Test
    public void testNegativeWeights() {
        IAlgoShortestPath algo = new BellmanFordAlgoShortestPathImpl();
        
        algo.addNode("A", 0, 0);
        algo.addNode("B", 1, 0);
        algo.addNode("C", 1, 1);
        
        algo.addEdge("A", "B", 4);
        algo.addEdge("B", "C", -2);
        algo.addEdge("A", "C", 3);
        
        PathResult result = algo.findShortestPath("A", "C");
        
        assertEquals(2.0, result.getTotalWeight(), 0.01);
        assertEquals(List.of("A", "B", "C"), result.getPath());
    }

    @Test
    public void testNegativeCycle() {
        IAlgoShortestPath algo = new BellmanFordAlgoShortestPathImpl();
        
        algo.addNode("A", 0, 0);
        algo.addNode("B", 1, 0);
        algo.addNode("C", 1, 1);
        
        // Create a negative cycle
        algo.addEdge("A", "B", -1);
        algo.addEdge("B", "C", -1);
        algo.addEdge("C", "A", -1);
        
        assertThrows(IllegalStateException.class, () -> {
            algo.findShortestPath("A", "C");
        });
    }

    @Test
    public void testNoPath() {
        IAlgoShortestPath algo = new BellmanFordAlgoShortestPathImpl();
        
        algo.addNode("A", 0, 0);
        algo.addNode("B", 1, 0);
        algo.addNode("C", 1, 1);
        
        algo.addEdge("A", "B", 1);
        // No path to C
        
        PathResult result = algo.findShortestPath("A", "C");
        
        assertEquals(Double.POSITIVE_INFINITY, result.getTotalWeight());
        assertTrue(result.getPath().isEmpty());
    }

    @Test
    public void testNonExistentNode() {
        IAlgoShortestPath algo = new BellmanFordAlgoShortestPathImpl();
        
        algo.addNode("A", 0, 0);
        algo.addNode("B", 1, 0);
        
        assertThrows(IllegalArgumentException.class, () -> {
            algo.findShortestPath("A", "Z"); // Z does not exist
        });
    }

    @Test
    public void testSameStartAndEnd() {
        IAlgoShortestPath algo = new BellmanFordAlgoShortestPathImpl();
        
        algo.addNode("A", 0, 0);
        algo.addNode("B", 1, 0);
        
        algo.addEdge("A", "B", 1);
        
        PathResult result = algo.findShortestPath("A", "A");
        
        assertEquals(0.0, result.getTotalWeight(), 0.01);
        assertEquals(List.of("A"), result.getPath());
    }
} 