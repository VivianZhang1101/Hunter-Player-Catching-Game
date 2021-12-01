// --== CS400 File Header Information ==--
// Name: Fangjun Zhou
// Email: fzhou48@wisc.edu
// Team: CG blue
// TA: Xi
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the implementation of CS400Graph for the individual component of
 * Project Three: the implementation of Dijsktra's Shortest Path algorithm.
 */
public class GraphTest {

    private CS400Graph<String> graph;
    
    /**
     * Instantiate graph from last week's shortest path activity.
     */
    @BeforeEach
    public void createGraph() {
        graph = new CS400Graph<>();
        // insert vertices A-E
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        // insert edges from Week 09. Dijkstra's Activity
        graph.insertEdge("A","B",2);
        graph.insertEdge("A","D",4);
        graph.insertEdge("A","E",1);
        graph.insertEdge("B","C",5);
        graph.insertEdge("C","A",3);
        graph.insertEdge("D","B",3);
        graph.insertEdge("D","C",7);
        graph.insertEdge("D","E",1);
        graph.insertEdge("E","C",8);
    }

    /**
     * Checks the distance/total weight cost from the vertex labelled C to E
     * (should be 4), and from the vertex labelled A to C (should be 7).
     */
    @Test
    public void providedTestToCheckPathCosts() {
        assertTrue(graph.getPathCost("C", "E") == 4);
        //System.out.println(graph.getPathCost("C", "E"));
        assertTrue(graph.getPathCost("A", "C") == 7);
        //System.out.println(graph.getPathCost("A", "C"));
        assertTrue(graph.getPathCost("D", "B") == 3);
        assertTrue(graph.getPathCost("E", "D") == 15);
    }

    /**
     * Checks the ordered sequence of data within vertices from the vertex 
     * labelled C to E, and from the vertex labelled A to C.
     */
    @Test
    public void providedTestToCheckPathContents() {
        assertTrue(graph.shortestPath("C", "E").toString().equals(
            "[C, A, E]"
        ));
        //System.out.println(graph.shortestPath("C", "E").toString());
        assertTrue(graph.shortestPath("A", "C").toString().equals(
            "[A, B, C]"
        ));
        //System.out.println(graph.shortestPath("A", "C").toString());
        assertTrue(graph.shortestPath("D", "B").toString().equals(
            "[D, B]"
        ));
        assertTrue(graph.shortestPath("E", "D").toString().equals(
            "[E, C, A, D]"
        ));
    }

}
