package com.utils;

import com.graph.Node;
import com.problem.MatchingProblem;
import org.graph4j.GraphBuilder;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

/**
 * The <tt>GraphUtils</tt> class has several methods to convert the bipartite graph stored in the {@link MatchingProblem} class into a <tt>Graph</tt>
 * object from an established library specialized in graph algorithms.
 */
public class GraphUtils {
    /**
     * Converts the bipartite graph stored in a <tt>MatchingProblem</tt> object into a {@link org.jgrapht.Graph}
     *
     * @param matchingProblem An instance of a matching problem that stores a bipartite graph
     * @return A {@link org.jgrapht.Graph} object
     * @see <a href="https://jgrapht.org">JGraphT Library</a>
     */
    public static org.jgrapht.Graph<Node, DefaultEdge> convertToJgrapht(MatchingProblem matchingProblem) {
        org.jgrapht.Graph<Node, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        for (Node node : matchingProblem.getAllNodes()) {
            graph.addVertex(node);
        }
        for (Node node : matchingProblem.getLeftSide()) {
            for (Node neig : matchingProblem.getNeighboursOfNode(node)) {
                graph.addEdge(node, neig);
            }
        }
        return graph;
    }

    /**
     * Converts the bipartite graph stored in a <tt>MatchingProblem</tt> object into a {@link org.graph4j.Graph}
     *
     * @param matchingProblem An instance of a matching problem that stores a bipartite graph
     * @return A {@link org.graph4j.Graph} object
     * @see <a href="https://profs.info.uaic.ro/~acf/graph4j/">Graph4J Library</a>
     */
    public static org.graph4j.Graph<Node, Integer> convertToGraph4J(MatchingProblem matchingProblem) {
        org.graph4j.Graph<Node, Integer> graph = GraphBuilder.empty().buildGraph();
        for (Node node : matchingProblem.getAllNodes()) {
            graph.addVertex(node);
        }
        for (Node node : matchingProblem.getLeftSide()) {
            for (Node neig : matchingProblem.getNeighboursOfNode(node)) {
                graph.addEdge(node, neig);
            }
        }
        return graph;
    }
}
