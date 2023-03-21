package graph;

import java.util.*;

public class Edge<N extends Comparable<N>, E extends Comparable<E>> implements Comparable<Edge<N, E>> {

    private final N startPoint;
    private final N endPoint;
    private final E label;

    private final static boolean DEBUG = false;

    public Edge(N startPoint, N endPoint, E label) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.label = label;
        checkRep();
    }

    // returns starting node
    public N getStartPoint() {
        checkRep();
        return startPoint;
    }

    // returns ending node
    public N getEndPoint() {
        checkRep();
        return endPoint;
    }

    // returns label of edge
    public E getLabel() {
        checkRep();
        return label;
    }


    //@Override
    public int compareTo(Edge<N, E> edge) {
        checkRep();
        if (!(startPoint.equals(edge.startPoint) && endPoint.equals(edge.endPoint) && label.equals(edge.label))) {
            if(!label.equals(edge.label)) {
                checkRep();
                return label.compareTo(edge.label);
            } else if(!endPoint.equals(edge.endPoint)) {
                checkRep();
                return endPoint.compareTo(edge.endPoint);
            } else if(!startPoint.equals(edge.startPoint)) {
                checkRep();
                return startPoint.compareTo(edge.startPoint);
            }
        }
        checkRep();
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        checkRep();
        if (!(o instanceof Edge<?, ?>)) {
            return false;
        }
        Edge<?, ?> edge = (Edge<?, ?>) o;
        checkRep();
        return startPoint.equals(edge.startPoint) && endPoint.equals(edge.endPoint) && label.equals(edge.label);
    }

    @Override
    public int hashCode() {
        checkRep();
        return startPoint.hashCode() + endPoint.hashCode() + label.hashCode();
    }

    private void checkRep() {
        if(DEBUG){
            assert(label != null) : "label == null";
            assert(startPoint != null) : "start == null";
            assert(endPoint != null) : "end == null";
        }
    }
}
