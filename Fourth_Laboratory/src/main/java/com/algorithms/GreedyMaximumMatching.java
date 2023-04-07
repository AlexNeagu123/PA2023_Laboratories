package com.algorithms;

import com.graph.Match;
import com.graph.Node;
import com.problem.MatchingProblem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is able to compute the maximum cardinality matching of a bipartite graph using a greedy algorithm.
 * <p>
 * Each node on the left side is matched with its first neighbour from the right side that hasn't been match already.
 * <p>
 * Time complexity of the algorithm: O(E + V) where E is the number of edges and V is the number of nodes in the graph.
 */
public class GreedyMaximumMatching extends MaximumMatchingAlgorithm {
    private final Set<Node> assignedNodes;

    public GreedyMaximumMatching(MatchingProblem matchingProblem) {
        super(matchingProblem);
        assignedNodes = new HashSet<>();
    }

    /**
     * @return A list of {@link Match} objects representing the maximum cardinality matching in a bipartite graph
     */
    @Override
    public List<Match> getMaxMatching() {
        List<Match> maxMatching = new ArrayList<>();
        for (Node node : matchingProblem.getLeftSide()) {
            for (Node neig : matchingProblem.getNeighboursOfNode(node)) {
                if (assignedNodes.contains(neig)) {
                    continue;
                }
                assignedNodes.add(neig);
                maxMatching.add(new Match(node, neig));
                break;
            }
        }
        return maxMatching;
    }
}
