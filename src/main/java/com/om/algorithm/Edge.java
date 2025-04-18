package com.om.algorithm;

public class Edge {
    private final String fromNode;
    private final String toNode;
    private final double weight;

    public Edge(String fromNode, String toNode, double weight) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.weight = weight;
    }

    public String getFromNode() {
        return fromNode;
    }

    public String getToNode() {
        return toNode;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return fromNode + " -> " + toNode + "(distance:" + weight + ")";
    }
}
