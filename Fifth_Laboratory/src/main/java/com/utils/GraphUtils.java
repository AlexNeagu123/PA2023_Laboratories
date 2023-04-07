package com.utils;

import com.entities.Document;
import com.problem.GraphColoringProblem;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

public class GraphUtils {
    /**
     * Converts the bipartite graph stored in a <tt>GraphColoringProblem</tt> object into a {@link org.jgrapht.Graph}
     *
     * @param graphColoringProblem An instance of a matching problem that stores a bipartite graph
     * @return A {@link org.jgrapht.Graph} object
     * @see <a href="https://jgrapht.org">JGraphT Library</a>
     */
    public static org.jgrapht.Graph<Document, DefaultEdge> convertToJgrapht(GraphColoringProblem graphColoringProblem) {
        org.jgrapht.Graph<Document, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        for (Document node : graphColoringProblem.getAllNodes()) {
            graph.addVertex(node);
        }
        for (Document node : graphColoringProblem.getAllNodes()) {
            for (Document neig : graphColoringProblem.getNeighboursOfNode(node)) {
                graph.addEdge(node, neig);
            }
        }
        return graph;
    }
}
