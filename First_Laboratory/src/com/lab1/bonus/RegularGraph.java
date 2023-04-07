package com.lab1.bonus;

/**
 * The {@code RegularGraph} class is able to build a regular graph.
 *
 * @author alexneagu
 * @see <a href="https://en.wikipedia.org/wiki/Regular_graph">Regular Graph</a>
 */
public class RegularGraph extends Graph {
    /**
     * Constructs a regular graph.
     *
     * @param nodeNr The number of nodes in the graph
     * @param kIndex The desired number of neighbours (degree) for each node
     * @throws IllegalArgumentException If it's impossible to construct a {@code kIndex} regular graph with {@code nodeNr} nodes
     */
    public RegularGraph(int nodeNr, int kIndex) throws IllegalArgumentException {
        super(nodeNr);
        if (kIndex >= nodeNr || nodeNr * kIndex % 2 == 1) {
            throw new IllegalArgumentException(
                    String.format("A %d-regular graph with %d nodes doesn't exist!", kIndex, nodeNr)
            );
        }

        /*
         *   Imagine all the nodes in a circular way.
         *   For each node, connect it with kIndex / 2 neighbours to the right and kIndex / 2 neighbours to the left
         *   If kIndex is odd do the step above and one extra step:
         *       Connect each node with its opposing node in the circle.
         */

        for (int nodeId = 0; nodeId < nodeNr; ++nodeId) {
            for (int dist = 1; dist <= kIndex / 2; ++dist) {
                int leftNeighbour = (nodeId - dist + nodeNr) % nodeNr;
                int rightNeighbour = (nodeId + dist) % nodeNr;
                adjMatrix[nodeId][leftNeighbour] = adjMatrix[leftNeighbour][nodeId] = 1;
                adjMatrix[nodeId][rightNeighbour] = adjMatrix[rightNeighbour][nodeId] = 1;
            }

            if (kIndex % 2 == 1) {
                int opposedNode = (nodeId + nodeNr / 2) % nodeNr;
                adjMatrix[nodeId][opposedNode] = adjMatrix[opposedNode][nodeId] = 1;
            }
        }
    }
}
