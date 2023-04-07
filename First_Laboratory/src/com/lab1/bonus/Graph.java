package com.lab1.bonus;

/**
 * The Graph class provides a skeletal implementation of the <tt>RegularGraph</tt> and <tt>CyclicGraph</tt> classes.
 * <p>
 * Important components in a graph such as the adjacency matrix and the number of nodes are stored in this class.
 *
 * @author alexneagu
 */

public class Graph {
    protected int[][] adjMatrix;
    protected int nodeNr;

    /**
     * Constructs a graph with {@code nodeNr} nodes and no edges.
     *
     * @param nodeNr The number of nodes in the graph
     */
    public Graph(int nodeNr) {
        this.nodeNr = nodeNr;
        this.adjMatrix = new int[nodeNr][nodeNr];
    }

    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    /**
     * The adjacency matrix is displayed on the screen
     */
    public void printAdjMatrix() {
        for (int row = 0; row < nodeNr; ++row) {
            for (int col = 0; col < nodeNr; ++col) {
                System.out.printf("%d ", adjMatrix[row][col]);
            }
            System.out.println();
        }
    }

    public int[][] cloneAdjMatrix() {
        int[][] clonedMatrix = new int[nodeNr][nodeNr];
        for (int row = 0; row < nodeNr; ++row) {
            System.arraycopy(adjMatrix[row], 0, clonedMatrix[row], 0, nodeNr);
        }
        return clonedMatrix;
    }
}