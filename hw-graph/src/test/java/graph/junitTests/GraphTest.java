package graph.junitTests;
import graph.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.List;

import static org.junit.Assert.*;

public class GraphTest {
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private Graph<String, String> graph = new Graph<>();
    private String node1 = "1";
    private String node2 = "2";
    private String node3 = "3";
    private String label1 = "12";

    @Test
    public void testSizeOnceConstructed() {

        assertEquals(0, graph.size());
    }

    @Test
    public void testAddingOneNode() {
        assertEquals(0, graph.size());
    }

    @Test
    public void testSizeAfterAddingOneNode() {
        testAddingOneNode();
        graph.addNode(node1);
        assertEquals(1, graph.size());
    }

    @Test
    public void testContainsNode1AfterAddingNodeA() {
        testAddingOneNode();
        graph.addNode(node1);
        assertTrue(graph.containsNode("1"));
    }

    @Test
    public void testContainsNode2AfterAddingNode1() {
        testAddingOneNode();
        graph.addNode(node1);
        assertFalse(graph.containsNode(node2));
    }

    @Test
    public void testAddEdge12WithoutAddingNode2() {
        testAddingOneNode();
        graph.addNode(node1);
        graph.addEdge(node1, node2, label1);
    }

    @Test
    public void testAddingTwoDifferentNodes() {
        testAddingOneNode();
        graph.addNode(node1);
        assertEquals(true, graph.containsNode("1"));
    }

    @Test
    public void testAddingReflexiveEdgeOnNode1() {
        testAddingOneNode();
        graph.addEdge(node1, node1, label1);
    }

    @Test
    public void testChildrenOfNodesOnNode1() {
        testAddingOneNode();
        graph.addNode(node1);
        assertEquals(List.of(), graph.childrenOfNode(node1));
    }




}
