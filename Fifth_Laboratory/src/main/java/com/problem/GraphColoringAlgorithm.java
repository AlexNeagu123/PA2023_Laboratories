package com.problem;

/**
 * The <tt>GraphColoringAlgorithm</tt> class provides a skeletal implementation for different graph coloring algorithms.
 */
public abstract class GraphColoringAlgorithm {
    protected final GraphColoringProblem graphColoringProblem;

    public GraphColoringAlgorithm(GraphColoringProblem graphColoringProblem) {
        this.graphColoringProblem = graphColoringProblem;
    }
}
