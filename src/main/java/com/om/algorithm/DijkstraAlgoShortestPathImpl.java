package com.om.algorithm;

import java.util.*;

public class DijkstraAlgoShortestPathImpl implements IAlgoShortestPath {

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
        graph.get(toNode).add(new Edge(toNode, fromNode, weight)); // Assuming undirected graph

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
        if(startNode == null || endNode == null || !graph.containsKey(startNode) || !graph.containsKey(endNode)) {
            throw new IllegalArgumentException("Start or end node does not exist in the graph: " + startNode + " → " + endNode);
        }

        Set<String> allNodes = new HashSet<>(); // Set to store all nodes
        Map<String, Double> distances = new HashMap<>(); // store the current shortest distance to each node
        Map<String, String> previousNodes = new HashMap<>(); //store the previous nodes in the shortest path
        PriorityQueue<NodeEntry> queue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance)); // Priority queue for Dijkstra's algorithm

        // collect all nodes in the graph including the start(fromNode) and end nodes(ToNode)
        for(Map.Entry<String, List<Edge>> entry : graph.entrySet()){
            String fromNode = entry.getKey();
            allNodes.add(fromNode);
            for(Edge edge : entry.getValue()){
                allNodes.add(edge.getToNode());
            }
        }
        //Initialize distances and previous nodes for all nodes
        for (String node : allNodes) {
            distances.put(node, Double.POSITIVE_INFINITY);
            previousNodes.put(node, null);
        }

        distances.put(startNode, 0.0);
        queue.add(new NodeEntry(startNode, 0.0));
        // Dijkstra's algorithm
        while(!queue.isEmpty()){
            NodeEntry current = queue.poll();

            //iterate through all edges of the current node
            for (Edge edge : graph.getOrDefault(current.name, new ArrayList<>())) { // Get edges for the current node
                String neighbor = edge.getToNode();
                double newDist = distances.get(current.name) + edge.getWeight();
                //Relaxation step
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousNodes.put(neighbor, current.name);
                    queue.add(new NodeEntry(neighbor, newDist));
                }

            }

        }
        // Reconstruct the shortest path
        List<String> path = new ArrayList<>();
        String currentNode = endNode;
        while (currentNode != null && previousNodes.containsKey(currentNode)) {
            path.add(currentNode);
            currentNode = previousNodes.get(currentNode);
        }
        Collections.reverse(path);// Reverse the path to get the correct order
        //if no path found , return empty path and infinite distance
        if (path.isEmpty() || !path.get(0).equals(startNode)) {
            return new PathResult(Collections.emptyList(), Double.POSITIVE_INFINITY);
        }
        double totalDistance = distances.get(endNode);
        return new PathResult(path, totalDistance); // Return the path and total distance
    }

    private static class NodeEntry { // Helper class for priority queue
        String name;
        double distance;

        NodeEntry(String name, double distance) {
            this.name = name;
            this.distance = distance;
        }
    }
}


