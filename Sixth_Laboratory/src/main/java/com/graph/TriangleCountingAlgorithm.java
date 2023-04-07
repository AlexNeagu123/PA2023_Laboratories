package com.graph;

import lombok.Data;
import lombok.Getter;
import org.graph4j.Graph;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

/**
 * The <tt>TriangleCountingAlgorithm</tt> class implements an algorithm for counting all the triangles in an undirected
 * graph built with <tt>Graph4J</tt> API.
 * <p>
 * The time complexity of the algorithm is an optimized O(V^3).
 */
@Data
public class TriangleCountingAlgorithm {
    int nodeCount;
    List<BitSet> bitSets;

    public TriangleCountingAlgorithm(Graph graph) {
        this.nodeCount = graph.numVertices();
        bitSets = new LinkedList<>();
        int[][] adjacencyMatrix = graph.adjacencyMatrix();
        for (int i = 0; i < nodeCount; ++i) {
            bitSets.add(new BitSet(this.nodeCount));
            for (int j = 0; j < nodeCount; ++j) {
                if (i == j) {
                    continue;
                }
                if (adjacencyMatrix[i][j] == 1) {
                    bitSets.get(i).set(j);
                }
            }
        }
    }

    public int countTriangles() {
        int triangleCount = 0;
        for (int i = 0; i < nodeCount; ++i) {
            for (int j = i + 1; j < nodeCount; ++j) {
                if (bitSets.get(i).get(j)) {
                    BitSet andBitSet = (BitSet) bitSets.get(i).clone();
                    andBitSet.and(bitSets.get(j));
                    triangleCount += andBitSet.cardinality();
                }
            }
        }
        return triangleCount / 3;
    }
}
