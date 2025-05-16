# Shortest Path Algorithms Library

A comprehensive Java library implementing various shortest path algorithms for graph-based pathfinding. This library provides a unified interface for different pathfinding algorithms, allowing easy comparison and integration into your projects.

## 🎯 Features

### Implemented Algorithms
- **Dijkstra's Algorithm**
  - Best for undirected graphs with non-negative weights
  - Finds the shortest path using a greedy approach
  - Time complexity: O(V log V + E)

- **A* (A-Star) Algorithm**
  - Enhanced pathfinding with heuristic guidance
  - Uses Euclidean distance heuristic for better performance
  - Ideal for spatial/coordinate-based graphs
  - Time complexity: O(V log V)

- **Bellman-Ford Algorithm**
  - Supports directed graphs with negative weights
  - Detects negative cycles
  - Can handle more general cases than Dijkstra
  - Time complexity: O(VE)

### Core Features
- Unified interface (`IAlgoShortestPath`) for all algorithms
- Support for both directed and undirected graphs
- Coordinate-based node system for spatial problems
- Comprehensive error handling
- Path reconstruction with total distance calculation

## 📋 Usage

### Project Setup
1. Clone the repository
2. Open the project in IntelliJ IDEA
3. The project uses JUnit 5 for testing, which is included in the project configuration

### Adding a Node
```java
IAlgoShortestPath algo = new DijkstraAlgoShortestPathImpl(); // or any other implementation
algo.addNode("A", 0, 0); // Add node with coordinates
```

### Adding Edges
```java
algo.addEdge("A", "B", 5.0); // Add edge with weight
```

### Finding Shortest Path
```java
PathResult result = algo.findShortestPath("A", "B");
List<String> path = result.getPath();
double distance = result.getTotalWeight();
```

### Algorithm Selection Guide
- Use **Dijkstra** for:
  - Undirected graphs
  - Non-negative weights
  - General pathfinding

- Use **A*** for:
  - Spatial/coordinate-based problems
  - When you need faster pathfinding with heuristics
  - Undirected graphs with non-negative weights

- Use **Bellman-Ford** for:
  - Directed graphs
  - Graphs with negative weights
  - When you need to detect negative cycles

## 🧪 Testing

The library includes comprehensive test cases for each algorithm. You can run the tests directly in IntelliJ IDEA:
1. Right-click on the `test` folder
2. Select "Run Tests"

Test cases cover:
- Basic pathfinding
- Edge cases (no path, same start/end)
- Negative weights (Bellman-Ford)
- Negative cycles detection
- Multiple equal paths
- Invalid inputs

## 🛠️ Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── om/
│               └── algorithm/
│                   ├── IAlgoShortestPath.java    # Common interface
│                   ├── Edge.java                  # Edge representation
│                   ├── PathResult.java            # Result wrapper
│                   ├── DijkstraAlgoShortestPathImpl.java
│                   ├── AStarAlgoShortestPathImpl.java
│                   └── BellmanFordAlgoShortestPathImpl.java
└── test/
    └── java/
        └── com/
            └── om/
                └── algorithm/
                    ├── DijkstraAlgoShortestPathTest.java
                    ├── AStarAlgoShortestPathTest.java
                    └── BellmanFordAlgoShortestPathTest.java
```

### Common Interface
All algorithms implement the `IAlgoShortestPath` interface:
```java
public interface IAlgoShortestPath {
    void addNode(String nodeName, double x, double y);
    void addEdge(String fromNode, String toNode, double weight);
    void removeNode(String nodeName);
    void removeEdge(String fromNode, String toNode);
    PathResult findShortestPath(String startNode, String endNode);
}
```

### Result Format
Results are returned in the `PathResult` class containing:
- Complete path as List<String>
- Total path weight/distance

## 🤝 Contributing

Contributions are welcome! Areas for potential expansion:
- Additional algorithms (Floyd-Warshall, Johnson's, etc.)
- More heuristic functions for A*
- Performance optimizations
- Additional test cases
- Build system integration (Maven/Gradle)

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

