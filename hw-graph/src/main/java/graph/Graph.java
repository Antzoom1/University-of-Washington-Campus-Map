package graph;

import java.util.*;

/**
 * The Graph class is an implementation of a directed, labeled graph that is mutable.
 *
 * <p> Graph can be described as two collections, one which stores the nodes of the graph.
 * The other list stores the edges of the Graph. The edges individually store information like their parent and
 * child nodes. The edges also store their labels as well.
 */
public class Graph<N extends Comparable<N>, E extends Comparable<E>> {
    // Rep Invariant:
    // graph != null (nodes != null, edges != null, nodes and edges doesn't contain null)
    // This would mean that every single node and edge in graph should not be null.
    // If n is included in an edge, graph must include n as well.

    // Abstract Functions:
    // AF(this) = graph g such that {}
    // this.nodes = [] and this.edges = [] when empty graph is constructed
    // or this.nodes = [x .. x_n] and this.edges = [] when graph contains any nodes.
    // this.nodes = [x ... x_n] and this.edges = [{startP, endP, l} ... {}_n] when graph
    // contains nodes and edges and where startP is the parent node, endP is the child node,
    // l is a label for this.nodes

    private Map<N, HashSet<Edge<N, E>>> graph;
    private final static boolean DEBUG = false;

    /**
     * Constructs an empty directed Graph that contains no nodes.
     *
     * @spec.effects constructs an empty Graph
     */
    public Graph() {
        graph = new LinkedHashMap<>();
        checkRep();
    }

    /**
     * Add node, n to the Graph if it doesn't exist.
     *
     * @param n node to be added into the Graph
     * @spec.requires n != null
     * @spec.modifies this.graph
     * @spec.effects adds node, n to the Graph if it doesn't exist already
     */
    public void addNode(N n) {
        checkRep();
        if (n != null) {
            if(!(graph.containsKey(n))) {
                graph.put(n, new HashSet<Edge<N, E>>());
            }
        }
        checkRep();
    }

    /**
     * Adds edge, from node startPoint to node endPoint with label l to the Graph if both nodes exist and the edge with label l,
     * doesn't exist
     *
     * @param startPoint node where edge starts
     * @param endPoint node where edge ends
     * @param l label of the edge
     * @spec.requires startPoint != null, endPoint != null, and l != null
     * @spec.modifies this.graph
     * @spec.effects Adds edge, from node startPoint to node endPoint, with l label, to the Graph if both nodes exist and the edge,
     * with label l, doesn't exist
     */
    public void addEdge(N startPoint, N endPoint, E l) {
        checkRep();
        if (startPoint != null && endPoint != null && l != null && graph.containsKey(startPoint) && graph.containsKey(endPoint)) {
            graph.get(startPoint).add(new Edge<N, E>(startPoint, endPoint, l));
        }
        checkRep();
    }

    /**
     * Returns given node n if node exists.
     * @return the list of nodes
     */
    public List<N> getNodes() {
        return new ArrayList<>(graph.keySet());
    }

    /**
     * Returns the number of nodes in the graph.
     *
     * @return number of nodes in the graph
     */
    public int size() {
        return graph.size();
    }

    /**
     * returns true if node n is in the graph.
     *
     * @param n node to find given node in the Graph
     * @return true if the Graph contains node, n, or false if it doesn't contain n
     */
    public boolean containsNode(N n) {
        checkRep();
        return graph.containsKey(n);
    }

    /**
     * Returns true only if the direct Graph contains the edge from startPoint to endPoint with the label l.
     * Otherwise, return false.
     *
     * @param startPoint node where edge starts
     * @param endPoint node where edge ends
     * @param l label of the edge
     * @return true if the direct Graph contains the edge, from node startPoint to node endPoint with label l.
     * Otherwise, false if it doesn't
     */
    public boolean containsEdge(N startPoint, N endPoint, E l) {
        checkRep();
        for (Map.Entry<N, HashSet<Edge<N, E>>> graphMap: graph.entrySet()) {
            if (graphMap.getValue().contains(new Edge<N, E>(startPoint, endPoint, l))) {
                checkRep();
                return true;
            }
        }
        checkRep();
        return false;
    }

    /**
     * Returns a set of the outgoing edges from a node if and only if the node exists in the graph
     *
     * @param n node to find outgoing edges from
     * @spec.requires n != null
     * @return set of outgoing edges from node, n, if it exists
     */
    public Set<Edge<N, E>> getEdges(N n) {
        checkRep();
        if (n == null || !(graph.containsKey(n))) {
            checkRep();
            return new HashSet<>();
        }
        checkRep();
        return new HashSet<>(graph.get(n));
    }

    /**
     * Returns a list of children nodes of node, n, if they currently exist.
     *
     * @param n node to find children of
     * @spec.requires n != null
     * @return a list of children nodes of node, n if they currently exist
     */
    public List<String> childrenOfNode(N n) {
        checkRep();
        if (n == null || !(graph.containsKey(n))) {
            checkRep();
            return List.of();
        }
        List<String> children = new ArrayList<>();
        for (Edge edge : graph.get(n)) {
            children.add(edge.getEndPoint() + "(" + edge.getLabel() + ")");
        }
        checkRep();
        return children;
    }

    /**
     * Throws an exception if the rep invariant is violated by the conditions
     */
    private void checkRep() {
        if(DEBUG){
            assert (graph != null) : "graph == null";
            for (N n : graph.keySet()) {
                assert (n != null) : "nodes == null";
            }
            for (Map.Entry<N, HashSet<Edge<N, E>>> graphMap: graph.entrySet()) {
                assert (!graphMap.getValue().contains(null)) : "edges == null";
            }
        }
    }
}

