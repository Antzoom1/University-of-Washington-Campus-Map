package marvel.junitTests;

import graph.Graph;
import marvel.MarvelPaths;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MarvelTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    private Graph graph = new Graph();

    @Test
    public void testIfEmpty() {
    graph = MarvelPaths.buildGraph("marvel.csv");
    assertTrue("marvelGraph is not empty", graph.size() != 0);
    }

    @Test
    public void testMarvelFile() {
        graph = MarvelPaths.buildGraph("marvel.csv");
        assertEquals(6435, graph.size());
    }
}
