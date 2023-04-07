package com.algorithms;

import com.problem.MatchingProblem;

/**
 * The <tt>GraphAlgorithm</tt> class provides a skeletal implementation for different algorithms related to bipartite graphs
 */
public abstract class GraphAlgorithm {
    protected final MatchingProblem matchingProblem;

    public GraphAlgorithm(MatchingProblem matchingProblem) {
        this.matchingProblem = matchingProblem;
    }
}
