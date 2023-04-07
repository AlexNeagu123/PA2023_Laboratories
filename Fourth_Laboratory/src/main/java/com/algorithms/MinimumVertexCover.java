package com.algorithms;

import com.graph.Node;
import com.problem.MatchingProblem;
import com.utils.GraphUtils;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.VertexCoverAlgorithm;
import org.jgrapht.alg.vertexcover.RecursiveExactVCImpl;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is able to compute the minimum vertex cover of a bipartite graph using a recursive algorithm
 * implemented in the <b>JGraphT</b> library.
 * <p>
 * The time complexity of this algorithm is exponential.
 *
 * @see <a href="https://jgrapht.org/">JGraphT</a>
 */
public class MinimumVertexCover extends GraphAlgorithm {
    public MinimumVertexCover(MatchingProblem matchingProblem) {
        super(matchingProblem);
    }

    /**
     * @return A list of nodes that form the minimum vertex cover
     */
    public List<Node> getMinimumVertexCover() {
        Graph<Node, DefaultEdge> graph = GraphUtils.convertToJgrapht(matchingProblem);
        VertexCoverAlgorithm<Node> vertexCoverAlgorithm = new RecursiveExactVCImpl<>(graph);
        return new ArrayList<>(vertexCoverAlgorithm.getVertexCover());
    }
}
