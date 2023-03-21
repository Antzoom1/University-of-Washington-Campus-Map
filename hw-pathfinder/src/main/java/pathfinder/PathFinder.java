package pathfinder;

import graph.Edge;
import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * PathFinder contains a method that finds the path at the lowest cost path of
 * two given nodes in a graph. These costs of the nodes must be non-negative.
 */

public class PathFinder {

    /**
     * Finds the least costly path between two given nodes
     *
     * @param graph graph to find path in
     * @param startPoint starting node
     * @param endPoint destination node
     * @param <N> comparable generic
     * @return a Path that finds the minimalist cost of a possible path or returns null
     * if the path doesn't exist.
     */
    public static <N extends Comparable<N>> Path<N> dijkstraPath(N startPoint, N endPoint, Graph<N, Double> graph) {
        Queue<Path<N>> visitingNodes = new PriorityQueue<>(new Comparator<Path<N>>() {
            public int compare(Path<N> o1, Path<N> o2) {
                return Double.compare(o1.getCost(), o2.getCost());
            }});
        if(startPoint == null || endPoint == null) {
            return null;
        }
        visitingNodes.add(new Path<N>(startPoint));
        Set<N> finishedNodes = new HashSet<>();
        while(!visitingNodes.isEmpty()) {
            Path<N> minPath = visitingNodes.remove();
            N minDestination = minPath.getEnd();
            if(minDestination.equals(endPoint)) {
                return minPath;
            }
            if((finishedNodes.contains(minDestination))) {
                continue;
            }
            for(Edge<N, Double> edge : graph.getEdges(minDestination)) {
                if(!(finishedNodes.contains(edge.getEndPoint()))) {
                    Path<N> newPathWay = minPath.extend(edge.getEndPoint(), edge.getLabel());
                    visitingNodes.add(newPathWay);
                }
            }
            finishedNodes.add(minDestination);

        }
        return null;
    }
}
