package com.algorithms;

import com.graph.Match;
import com.graph.Node;
import com.problem.MatchingProblem;
import com.utils.GraphUtils;
import org.graph4j.Graph;
import org.graph4j.alg.matching.HopcroftKarpMaximumMatching;
import org.graph4j.util.Matching;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is able to compute the maximum cardinality matching of a bipartite graph using the Hopcroft Karp algorithm
 * implemented in the <b>Graph4J</b> library.
 * <p>
 * Time complexity of the algorithm: O(E * sqrt(V)) where E is the number of edges and V is the number of nodes in the graph.
 *
 * @see <a href="https://profs.info.uaic.ro/~acf/graph4j/">Graph4J</a>
 */
public class Graph4jMaximumMatching extends MaximumMatchingAlgorithm {
    public Graph4jMaximumMatching(MatchingProblem matchingProblem) {
        super(matchingProblem);
    }

    /**
     * @return A list of {@link Match} objects representing the maximum cardinality matching in a bipartite graph
     */
    @Override
    public List<Match> getMaxMatching() {
        Graph<Node, Integer> graph = GraphUtils.convertToGraph4J(matchingProblem);
        HopcroftKarpMaximumMatching hopcroftKarpMaximumMatching = new HopcroftKarpMaximumMatching(graph);
        Matching maxMatchingGraph = hopcroftKarpMaximumMatching.getMatching();
        List<Match> maxMatching = new ArrayList<>();
        for (int i = 0; i < maxMatchingGraph.numEdges(); ++i) {
            int firstEndpoint = maxMatchingGraph.edges()[i][0];
            int secondEndpoint = maxMatchingGraph.edges()[i][1];
            maxMatching.add(new Match(graph.getVertexLabel(firstEndpoint), graph.getVertexLabel(secondEndpoint)));
        }
        return maxMatching;
    }
}
