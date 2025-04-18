package com.om.algorithm;

public interface IAlgoShortestPath{

    void addNode(String nodeName, double x, double y);

    void addEdge(String fromNode, String toNode, double weight);

    void removeNode(String nodeName);

    void removeEdge(String fromNode, String toNode);

    PathResult findShortestPath(String startNode, String endNode);
}
