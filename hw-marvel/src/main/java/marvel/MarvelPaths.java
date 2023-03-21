package marvel;

import graph.Edge;
import graph.Graph;

import java.io.File;
import java.util.*;

/**
 *
 * MarvelPaths is a class that can build a graph using a scanner that uses the data from the
 * input file to find lexicographically, the fastest path between the two nodes.
 *  */


public class MarvelPaths {

    /**
     *
     * This method builds a graph based off the data from the input file.
     *
     * @param fileName file that is used to build the graph
     * @return the graph that is created using the file data in fileName
     */

    public static Graph<String, String> buildGraph(String fileName) {
        Graph<String, String> marvelGraph = new Graph<>();
        HashSet<String> characters = new LinkedHashSet<>();
        HashMap<String, List<String>> books = new HashMap<>();
        MarvelParser.parseData(fileName, characters, books);
        for (String s : characters) {
            marvelGraph.addNode(s);
        }
        for (String bookLabel : books.keySet()) {
            for (int i = 0; i < books.get(bookLabel).size(); i++) {
                String startingPoint = books.get(bookLabel).get(i);
                for (int j = i + 1; j < books.get(bookLabel).size(); j++) {
                    String endingPoint = books.get(bookLabel).get(j);
                    marvelGraph.addEdge(endingPoint, startingPoint, bookLabel);
                    marvelGraph.addEdge(startingPoint, endingPoint, bookLabel);

                }
            }
        }
        return marvelGraph;
    }

    /**
     * Finds the fastest path betweeen two nodes in lexicographically order
     *
     * @param graph graph to find path in
     * @param startPoint starting node
     * @param endPoint destination node
     * @return a 2D list that contains the fastest path between the startPoint and the endPoint in lexicographical order
     */
    public static List<List<String>> findPath(String startPoint, String endPoint, Graph<String, String> graph) {
        if(graph == null || startPoint == null || !graph.containsNode(startPoint) || endPoint == null || !graph.containsNode(endPoint)) {
            return null;
        }
        Map<String, List<List<String>>> pathWay = new HashMap<>();
        Queue<String> visitingNodes = new LinkedList<>();
        visitingNodes.add(startPoint);
        pathWay.put(startPoint, new ArrayList<>());
        while(!visitingNodes.isEmpty()) {
            String character = visitingNodes.remove();
            if(character.equals(endPoint)) {
                return pathWay.get(character);
            }
            Set<Edge<String, String>> edges = new TreeSet<>(new EdgeComparator());
            edges.addAll(graph.getEdges(character));
            for(Edge<String, String> edge : edges) {
                if(!pathWay.containsKey(edge.getEndPoint()) && edge.getStartPoint().equals(character)) {
                    List<List<String>> newPath = new ArrayList<>(pathWay.get(character));
                    List<String> edgeList = new ArrayList<>(List.of(edge.getStartPoint(), edge.getLabel(), edge.getEndPoint()));
                    newPath.add(edgeList);
                    pathWay.put(edge.getEndPoint(), newPath);
                    visitingNodes.add(edge.getEndPoint());
                }
            }
        }
        return null;
    }

    private static class EdgeComparator implements Comparator<Edge<String, String>> {
        public int compare(Edge<String, String> edge1, Edge<String, String> edge2) {
            if(!(edge1.getEndPoint().equals(edge2.getEndPoint()))) {
                return edge1.getEndPoint().compareTo(edge2.getEndPoint());
            }
            if(!(edge1.getLabel().equals(edge2.getLabel()))) {
                return edge1.getLabel().compareTo(edge2.getLabel());
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner kd = new Scanner(System.in);
        Graph<String, String> graph = buildingGraph(kd);
        System.out.println("Calculating paths...");
        System.out.println();
        findPath(kd, graph);
        System.out.println();
        System.out.println("Thanks for playing!");
        System.out.println("Now closing program...");
        kd.close();


        Graph<String, String> marvelGraph = buildGraph("staffSuperheroes.csv");
        List<List<String>> pathWay = findPath("Ernst-the-Bicycling-Wizard", "Notkin-of-the-Superhuman-Beard", marvelGraph);
        System.out.println(pathWay);
    }

    private static Graph<String, String> buildingGraph(Scanner console) {
        System.out.println("Hello! Welcome to Marvel!");
        System.out.println();
        System.out.println("Enter a file name: ");
        String fileName = console.nextLine();
        File file = new File("src/hw6/data/" + fileName);
        System.out.println();
        return buildGraph(fileName);


    }
    private static void findPath(Scanner console, Graph<String, String> graph) {
        System.out.print("Input a starting character: ");
        String startPoint = console.nextLine();
        System.out.print("Input a ending character: ");
        String endPoint = console.nextLine();
        System.out.println(findPath(startPoint, endPoint, graph));
    }
}





