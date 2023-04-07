package com.lab1.bonus;

/**
 * A class that contains the solution for the second bullet.
 *
 * @author alexneagu
 */
public class RegularGraphSolver {
    /**
     * A regular graph with {@code nodeNr} nodes and a {@code kIndex} degree for each node is created.
     * <p>
     * If it's possible to create such a graph, the adjacency matrix is displayed on the screen.
     * Otherwise, an informative message is displayed on the screen.
     *
     * @param nodeNr The number of nodes in the graph.
     * @param kIndex Degree for each node.
     */
    public static void run(int nodeNr, int kIndex) {
        try {
            RegularGraph regularGraph = new RegularGraph(nodeNr, kIndex);
            System.out.printf("A %d-regular graph with %d nodes has been built and has the following adjacency matrix\n", kIndex, nodeNr);
            regularGraph.printAdjMatrix();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}