public class DijkstraTest {
    public static void main(String[] args) {
        IAlgoShortestPath algo = new DijkstraAlgoShortestPathImpl();
        algo.addNode("Library", 0.0, 0.0);
        algo.addNode("Lab", 220.0, 1.0);
        algo.addNode("Park", 5.0, 0.0);
        algo.addNode("Science Room", 4.0, 3.0);

        // Adding weighted edges
        algo.addEdge("Library", "Lab", 2.5);
        algo.addEdge("Library", "Park", 6.0);
        algo.addEdge("Lab", "Park", 3.0);
        algo.addEdge("Lab", "Science Room", 2.5);
        algo.addEdge("Park", "Science Room", 2.0);

        // Run shortest path from Science Room to Library
        PathResult result = algo.findShortestPath("Science Room", "Library");

        System.out.println("Using A*:");
        System.out.println(result);
    }
}