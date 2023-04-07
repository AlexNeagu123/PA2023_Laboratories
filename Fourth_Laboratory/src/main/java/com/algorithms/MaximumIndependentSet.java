package com.algorithms;

import com.graph.Node;
import com.problem.MatchingProblem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is able to compute maximum independent set of a bipartite by firstly computing the minimum vertex cover
 * using a recursive algorithm implemented in the <b>JGraphT</b> library.
 * <p>
 * After the minimum vertex cover is computed, all the nodes that aren't contained in the vertex cover set are added
 * to the maximum independent set.
 * <p>
 * The time complexity of this algorithm is exponential (because of the minimum vertex cover).
 *
 * @see <a href="https://jgrapht.org/">JGraphT</a>
 */
public class MaximumIndependentSet extends GraphAlgorithm {
    public MaximumIndependentSet(MatchingProblem matchingProblem) {
        super(matchingProblem);
    }

    /**
     * @return A list of nodes that forms the maximum independent set
     */
    public List<Node> getMaximumIndependentSet() {
        MinimumVertexCover vertexCoverAlgorithm = new MinimumVertexCover(matchingProblem);
        Set<Node> minimumVertexCover = new HashSet<>(vertexCoverAlgorithm.getMinimumVertexCover());
        Set<Node> allNodes = matchingProblem.getAllNodes();
        List<Node> maximumIndependentSet = new ArrayList<>();
        allNodes.stream().filter(n -> !minimumVertexCover.contains(n)).forEach(maximumIndependentSet::add);
        return maximumIndependentSet;
    }
}
