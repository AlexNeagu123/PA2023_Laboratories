package com.problem;

import com.entities.Document;

import java.util.*;

/**
 * This class is able to solve the graph coloring problem using a greedy algorithm.
 * <p>
 * The algorithm iterates through all nodes. For every processed node, a color is chosen that differs to the colors of
 * its neighbours.
 * <p>
 * Time complexity of the algorithm: O(E + V^2) where E is the number of edges and V is the number of nodes in the graph.
 */
public class GreedyColoring extends GraphColoringAlgorithm {
    private final Map<Document, Integer> coloring;

    public GreedyColoring(GraphColoringProblem graphColoringProblem) {
        super(graphColoringProblem);
        coloring = new HashMap<>();
    }

    /**
     * Given a set of integers representing colors, find the first color (integer) that is not in the set.
     *
     * @param usedColors A set of used colors.
     * @return The first integer (color) that
     */
    private int firstAvailable(Set<Integer> usedColors) {
        int color = 1;
        while (true) {
            if (!usedColors.contains(color)) {
                return color;
            }
            color++;
        }
    }

    /**
     * @return A list of {@link NodeColorPair} objects representing the coloring of the graph
     */
    public List<NodeColorPair> getColoring() {
        for (Document node : graphColoringProblem.getAllNodes()) {
            Set<Integer> usedColors = new HashSet<>();
            for (Document neig : graphColoringProblem.getNeighboursOfNode(node)) {
                if (coloring.containsKey(neig)) usedColors.add(coloring.get(neig));
            }
            coloring.put(node, firstAvailable(usedColors));
        }
        List<NodeColorPair> graphColoring = new ArrayList<>();
        for (Document node : coloring.keySet()) {
            graphColoring.add(new NodeColorPair(node, coloring.get(node)));
        }
        return graphColoring;
    }
}
