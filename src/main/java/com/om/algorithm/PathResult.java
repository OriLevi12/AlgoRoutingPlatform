package com.om.algorithm;

import java.util.List;

public class PathResult {
    private final List<String> path;
    private final double totalWeight;

    public PathResult(List<String> path, double totalWeight) {
        this.path = path;
        this.totalWeight = totalWeight;
    }

    public List<String> getPath() {
        return path;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    @Override
    public String toString() {
        return "Path: " + String.join(" -> ", path) + "| Distance: " + totalWeight;
    }
}
