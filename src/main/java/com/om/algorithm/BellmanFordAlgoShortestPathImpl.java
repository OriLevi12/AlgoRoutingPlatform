package com.om.algorithm;

import java.util.*;

public class BellmanFordAlgoShortestPathImpl implements IAlgoShortestPath {
    private final Map<String, List<Edge>> graph = new HashMap<>();

    @Override
    public void addNode(String nodeName, double x, double y) {
        graph.putIfAbsent(nodeName, new ArrayList<>());
    }

    @Override
    public void addEdge(String fromNode, String toNode, double weight) {
        graph.putIfAbsent(fromNode, new ArrayList<>());
        graph.putIfAbsent(toNode, new ArrayList<>());
        graph.get(fromNode).add(new Edge(fromNode, toNode, weight));
    }

    @Override
    public void removeNode(String nodeName) {
        graph.remove(nodeName);
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
        if (startNode == null || endNode == null || !graph.containsKey(startNode) || !graph.containsKey(endNode)) {
            throw new IllegalArgumentException("Start or end node does not exist in the graph: " + startNode + " → " + endNode);
        }

        // Collect all nodes in the graph
        Set<String> allNodes = new HashSet<>();
        for (Map.Entry<String, List<Edge>> entry : graph.entrySet()) {
            allNodes.add(entry.getKey());
            for (Edge edge : entry.getValue()) {
                allNodes.add(edge.getToNode());
            }
        }

        // Initialize distances and previous nodes
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        for (String node : allNodes) {
            distances.put(node, Double.POSITIVE_INFINITY);
            previousNodes.put(node, null);
        }
        distances.put(startNode, 0.0);

        // Get all edges in the graph
        List<Edge> allEdges = new ArrayList<>();
        for (List<Edge> edges : graph.values()) {
            allEdges.addAll(edges);
        }

        // Relax edges |V| - 1 times
        int numNodes = allNodes.size();
        for (int i = 1; i < numNodes; i++) {
            for (Edge edge : allEdges) {
                if (distances.get(edge.getFromNode()) != Double.POSITIVE_INFINITY &&
                    distances.get(edge.getFromNode()) + edge.getWeight() < distances.get(edge.getToNode())) {
                    distances.put(edge.getToNode(), distances.get(edge.getFromNode()) + edge.getWeight());
                    previousNodes.put(edge.getToNode(), edge.getFromNode());
                }
            }
        }

        // Check for negative cycles
        for (Edge edge : allEdges) {
            if (distances.get(edge.getFromNode()) != Double.POSITIVE_INFINITY &&
                distances.get(edge.getFromNode()) + edge.getWeight() < distances.get(edge.getToNode())) {
                throw new IllegalStateException("Graph contains a negative weight cycle");
            }
        }

        // Reconstruct the path
        List<String> path = new ArrayList<>();
        String currentNode = endNode;
        while (currentNode != null && previousNodes.containsKey(currentNode)) {
            path.add(currentNode);
            currentNode = previousNodes.get(currentNode);
        }
        Collections.reverse(path);

        // If no path found, return empty path and infinite distance
        if (path.isEmpty() || !path.get(0).equals(startNode)) {
            return new PathResult(Collections.emptyList(), Double.POSITIVE_INFINITY);
        }

        return new PathResult(path, distances.get(endNode));
    }
} 