package com.om.algorithm;

import java.awt.geom.Point2D;
import java.util.*;

public class AStarAlgoShortestPathImpl implements IAlgoShortestPath {

    private final Map<String, List<Edge>> graph = new HashMap<>();
    private final Map<String, Point2D> positions = new HashMap<>();

    @Override
    public void addNode(String nodeName, double x, double y) {
        graph.putIfAbsent(nodeName, new ArrayList<>());
        positions.putIfAbsent(nodeName, new Point2D.Double(x, y));
    }

    @Override
    public void addEdge(String fromNode, String toNode, double weight) {
        graph.putIfAbsent(fromNode, new ArrayList<>());
        graph.putIfAbsent(toNode, new ArrayList<>());
        graph.get(fromNode).add(new Edge(fromNode, toNode, weight));
        graph.get(toNode).add(new Edge(toNode, fromNode, weight)); // Assuming undirected graph
    }

    @Override
    public void removeNode(String nodeName) {
        graph.remove(nodeName);
        positions.remove(nodeName);
        for (List<Edge> edges : graph.values()) {
            edges.removeIf(edge -> edge.getToNode().equals(nodeName));
        }
    }

    @Override
    public void removeEdge(String fromNode, String toNode) {
        List<Edge> edgesFrom = graph.get(fromNode);
        if (edgesFrom != null) {
            edgesFrom.removeIf(edge -> edge.getToNode().equals(toNode));
        }
        List<Edge> edgesTo = graph.get(toNode);
        if (edgesTo != null) {
            edgesTo.removeIf(edge -> edge.getToNode().equals(fromNode));
        }
    }

    @Override
    public PathResult findShortestPath(String startNode, String endNode) {
        if(startNode == null || endNode == null || !graph.containsKey(startNode) || !graph.containsKey(endNode)) {
            throw new IllegalArgumentException("Start or end node does not exist in the graph: " + startNode + " → " + endNode);
        }

        // Collect all nodes in the graph for initialization
        Set<String> allNodes = new HashSet<>();
        for (Map.Entry<String, List<Edge>> entry : graph.entrySet()) {
            allNodes.add(entry.getKey());
            for (Edge edge : entry.getValue()) {
                allNodes.add(edge.getToNode());
            }
        }
        // g(n): Cost from start to n
        Map<String, Double> gScore = new HashMap<>();
        // f(n): Estimated total cost from start to goal through n
        Map<String, Double> fScore = new HashMap<>();
        // For path reconstruction
        Map<String, String> cameFrom = new HashMap<>(); // To reconstruct the path

        // Initialize scores to infinity for all nodes
        for(String node : allNodes) {
            gScore.put(node, Double.POSITIVE_INFINITY);
            fScore.put(node, Double.POSITIVE_INFINITY);
        }

        // Initialize start node with g=0 and f=heuristic estimate
        gScore.put(startNode, 0.0);
        fScore.put(startNode, heuristic(startNode, endNode));

        // Priority queue ordered by fScore (estimated total cost)
        PriorityQueue<NodeEntry> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));
        openSet.add(new NodeEntry(startNode, 0.0 , fScore.get(startNode)));

        while(!openSet.isEmpty()) {
            NodeEntry current = openSet.poll();

            // Check if we reached the end node
            if (current.name.equals(endNode)) {
                return reconstructPath(cameFrom, endNode, gScore.get(endNode));
            }

            // Explore neighbors
            for (Edge edge : graph.getOrDefault(current.name, new ArrayList<>())) {
                String neighbor = edge.getToNode();
                double tentativeG = gScore.get(current.name) + edge.getWeight(); // Cost from start to neighbor

                // If we found a better path to neighbor
                if (tentativeG < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current.name);
                    gScore.put(neighbor, tentativeG);
                    // Update fScore with new g + heuristic
                    fScore.put(neighbor, tentativeG + heuristic(neighbor, endNode));

                    openSet.add(new NodeEntry(neighbor, tentativeG, fScore.get(neighbor)));
                }
            }
        }
        return new PathResult(Collections.emptyList(), Double.POSITIVE_INFINITY); // No path found

    }

    private PathResult reconstructPath(Map<String, String> cameFrom, String end, double totalDist) {
        List<String> path = new ArrayList<>();
        String current = end;

        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }

        Collections.reverse(path);
        return new PathResult(path, totalDist);
    }

    // Heuristic function to estimate the distance from a node to the goal
    private double heuristic(String from, String to){
        Point2D p1 = positions.get(from);
        Point2D p2 = positions.get(to);
        if(p1 == null || p2 == null) {
            return Double.MAX_VALUE; // or some large value
        }
        return p1.distance(p2); // Euclidean distance
    }

    private static class NodeEntry {
        String name;
        double gScore; // Cost from start to this node
        double fScore; // Estimated cost from start to goal through this node

        NodeEntry(String name, double gScore, double fScore) {
            this.name = name;
            this.gScore = gScore;
            this.fScore = fScore;
        }
    }
}


