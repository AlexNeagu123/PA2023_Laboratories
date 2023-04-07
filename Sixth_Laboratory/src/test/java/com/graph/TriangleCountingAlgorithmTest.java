package com.graph;

import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TriangleCountingAlgorithmTest {
    @Test
    void shouldCountFourTriangles_whenGraphIsCompleteWithFourNodes() {
        Graph graph = GraphBuilder.numVertices(4).buildGraph();
        for (int i = 0; i < 4; ++i) {
            for (int j = i + 1; j < 4; ++j) {
                graph.addEdge(i, j);
            }
        }
        TriangleCountingAlgorithm triangleCountingAlgorithm = new TriangleCountingAlgorithm(graph);
        Assertions.assertEquals(4, triangleCountingAlgorithm.countTriangles());
    }

    @Test
    void shouldCountZeroTriangles_whenGraphIsACycleWithFiveNodes() {
        Graph graph = GraphBuilder.numVertices(5).buildGraph();
        for (int i = 0; i < 4; ++i) {
            graph.addEdge(i, (i + 1) % 4);
        }
        TriangleCountingAlgorithm triangleCountingAlgorithm = new TriangleCountingAlgorithm(graph);
        Assertions.assertEquals(0, triangleCountingAlgorithm.countTriangles());
    }

    @Test
    void shouldCountOneTriangle_whenGraphIsATriangle() {
        Graph graph = GraphBuilder.numVertices(4).buildGraph();
        for (int i = 0; i < 3; ++i) {
            for (int j = i + 1; j < 3; ++j) {
                graph.addEdge(i, j);
            }
        }
        TriangleCountingAlgorithm triangleCountingAlgorithm = new TriangleCountingAlgorithm(graph);
        Assertions.assertEquals(1, triangleCountingAlgorithm.countTriangles());
    }
}