package com.algorithms;

import com.graph.Match;
import com.graph.Node;
import com.problem.MatchingProblem;
import com.utils.GraphUtils;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is able to compute the maximum cardinality matching of a bipartite graph using the Hopcroft Karp algorithm
 * implemented in the <b>JGraphT</b> library.
 * <p>
 * Time complexity of the algorithm: O(E * sqrt(V)) where E is the number of edges and V is the number of nodes in the graph.
 *
 * @see <a href="https://jgrapht.org/">JGraphT</a>
 */
public class JgraphtMaximumMatching extends MaximumMatchingAlgorithm {
    public JgraphtMaximumMatching(MatchingProblem matchingProblem) {
        super(matchingProblem);
    }

    /**
     * @return A list of {@link Match} objects representing the maximum cardinality matching in a bipartite graph
     */
    @Override
    public List<Match> getMaxMatching() {
        Graph<Node, DefaultEdge> graph = GraphUtils.convertToJgrapht(matchingProblem);

        HopcroftKarpMaximumCardinalityBipartiteMatching<Node, DefaultEdge> hopcroftKarp =
                new HopcroftKarpMaximumCardinalityBipartiteMatching<>(graph, matchingProblem.getLeftSide(), matchingProblem.getRightSide());

        MatchingAlgorithm.Matching<Node, DefaultEdge> jgraphtMatching = hopcroftKarp.getMatching();
        List<Match> maxMatching = new ArrayList<>();
        for (DefaultEdge e : jgraphtMatching.getEdges()) {
            maxMatching.add(new Match(graph.getEdgeSource(e), graph.getEdgeTarget(e)));
        }
        return maxMatching;
    }
}
