package com.lab1.bonus;

/**
 * A class that contains the solution for the first bullet.
 *
 * @author alexneagu
 */
public class CyclicGraphSolver {
    /**
     * A Cyclic Graph with {@code nodeNr} number of nodes is created.
     * <p>
     * The adjacency matrix of the graph is sequentially raised to powers from 2 to {@code nodeNr}.
     * <p>
     * Informative messages are displayed on the screen
     *
     * @param nodeNr The number of nodes of the cyclic graph
     */
    public static void run(int nodeNr) {
        try {
            CyclicGraph cyclicGraph = new CyclicGraph(nodeNr);
            MatrixUtils.printPoweredMatrix(1, cyclicGraph.getAdjMatrix());

            int[][] powerMatrix = cyclicGraph.cloneAdjMatrix();
            for (int i = 2; i <= nodeNr; ++i) {
                powerMatrix = MatrixUtils.multiply(powerMatrix, cyclicGraph.getAdjMatrix());
                MatrixUtils.printPoweredMatrix(i, powerMatrix);
            }
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}