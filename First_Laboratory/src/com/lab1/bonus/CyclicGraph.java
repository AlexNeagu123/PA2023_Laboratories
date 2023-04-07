package com.lab1.bonus;

/**
 * The {@code CyclicGraph} class is able to build a cycle graph.
 */
public class CyclicGraph extends Graph {
    /**
     * Constructs a cycle graph with {@code nodeNr} nodes.
     *
     * @param nodeNr The number of nodes in the graph
     */
    public CyclicGraph(int nodeNr) {
        super(nodeNr);
        for (int nodeId = 0; nodeId < nodeNr; ++nodeId) {
            adjMatrix[nodeId][(nodeId + 1) % nodeNr] = adjMatrix[(nodeId + 1) % nodeNr][nodeId] = 1;
        }
    }
}