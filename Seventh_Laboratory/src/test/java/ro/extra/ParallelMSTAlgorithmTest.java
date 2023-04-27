package ro.extra;

import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.junit.Assert;
import org.junit.Test;

public class ParallelMSTAlgorithmTest {
    @Test
    public void shouldFindASpanningTree_ofTotalCost9_whenComplexGraphGiven() {
        Graph graph = GraphBuilder.numVertices(6)
                .addEdge(0, 1)
                .addEdge(0, 2)
                .addEdge(0, 3)
                .addEdge(2, 3)
                .addEdge(0, 4)
                .addEdge(1, 4)
                .addEdge(1, 5)
                .addEdge(2, 5)
                .addEdge(1, 2)
                .buildGraph();

        graph.setEdgeWeight(0, 1, 4);
        graph.setEdgeWeight(0, 2, 6);
        graph.setEdgeWeight(0, 3, 1);
        graph.setEdgeWeight(2, 3, 8);
        graph.setEdgeWeight(0, 4, 2);
        graph.setEdgeWeight(1, 4, 1);
        graph.setEdgeWeight(1, 5, 2);
        graph.setEdgeWeight(2, 5, 4);
        graph.setEdgeWeight(1, 2, 3);

        ParallelMSTAlgorithm algorithm = new ParallelMSTAlgorithm(graph);
        int mstCost = 0;
        for (Edge edge : algorithm.computeMST()) {
            mstCost += edge.getCost();
        }

        Assert.assertEquals(mstCost, 9);
    }
}