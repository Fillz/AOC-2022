import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * Dijkstra implementation taken from https://www.baeldung.com/java-dijkstra
 */

public class Day12 {
    public static void main(String[] args) {
        new Day12();
    }
    public Day12() {
        ArrayList<String> input = ReadInput.read("res/input12.txt");
		partOne(input);
		partTwo(input);
    }

	private void partOne(ArrayList<String> input) {
        parseInput(input);
        calculateShortestPathFromSource(this.graph, this.start);
        System.out.println(end.distance);
	}

	private void partTwo(ArrayList<String> input) {
        parseInput(input);

        ArrayList<Node> lowestNodes = new ArrayList<Node>();
        for(Node n : graph.nodes) {
            if(n.height == 0)
                lowestNodes.add(n);
        }

        int shortest = Integer.MAX_VALUE;
        for(int i = 0; i < lowestNodes.size(); i++) {
            System.out.println("Processing: " + (i+1) + "/" + lowestNodes.size());
            Node low = graph.getNode(lowestNodes.get(i).x, lowestNodes.get(i).y);
            calculateShortestPathFromSource(this.graph, low);
            if(end.distance < shortest)
                shortest = end.distance;
            parseInput(input);
        }
        System.out.println(shortest);
	}

    private Graph graph;
    private Node start;
    private Node end;

    private void parseInput(ArrayList<String> input) {
        this.graph = new Graph();
        for(int y = 0; y < input.size(); y++) {
			for(int x = 0; x < input.get(0).length(); x++) {
                Node n;
                char c = input.get(y).charAt(x);
				if(c == 'S') {
					start = new Node("" + x + "," + y, x, y, 0);
					n = start;
				}
				else if(c == 'E') {
					end = new Node("" + x + "," + y, x, y, 25);
					n = end;
				}
				else
					n = new Node("" + x + "," + y, x, y, getCharValue(c));
                graph.addNode(n);
			}
		}
        for(int y = 0; y < input.size(); y++) {
			for(int x = 0; x < input.get(0).length(); x++) {
                Node n = graph.getNode(x, y);

                Node left = graph.getNode(x - 1, y);
                if(left != null && left.height - 1 <= n.height)
                    n.addDestination(left, 1);
                Node right = graph.getNode(x + 1, y);
                if(right != null && right.height - 1 <= n.height)
                    n.addDestination(right, 1);
                Node up = graph.getNode(x, y - 1);
                if(up != null && up.height - 1 <= n.height)
                    n.addDestination(up, 1);
                Node down = graph.getNode(x, y + 1);
                if(down != null && down.height - 1 <= n.height)
                    n.addDestination(down, 1);
			}
		}
    }

    private int getCharValue(char c) {
		return Character.getNumericValue(c) - 10;
	}

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);
    
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
    
        unsettledNodes.add(source);
    
        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Entry < Node, Integer> adjacencyPair: 
              currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
    }
}

    private class Graph {

        private Set<Node> nodes = new HashSet<>();
        
        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }
    
        public Node getNode(int x, int y) {
            for(Node n : nodes) {
                if(n.x == x && n.y == y)
                    return n;
            }
            return null;
        }
    }

    private class Node {
    
        private String name;
        private List<Node> shortestPath = new LinkedList<>();
        private Integer distance = Integer.MAX_VALUE;
        Map<Node, Integer> adjacentNodes = new HashMap<>();
        public int x, y, height;
    
        public void addDestination(Node destination, int distance) {
            adjacentNodes.put(destination, distance);
        }
     
        public Node(String name, int x, int y, int height) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.height = height;
        }
        
        public int getDistance() {
            return this.distance;
        }
        public void setDistance(int d) {
            this.distance = d;
        }
        public List<Node> getShortestPath() {
            return this.shortestPath;
        }
        public void setShortestPath(List<Node> shortestPath) {
            this.shortestPath = shortestPath;
        }
        public Map<Node, Integer> getAdjacentNodes() {
            return this.adjacentNodes;
        }
    }
}
