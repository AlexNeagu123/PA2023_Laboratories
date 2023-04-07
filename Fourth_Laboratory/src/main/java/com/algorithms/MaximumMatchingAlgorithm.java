package com.algorithms;

import com.graph.Match;
import com.problem.MatchingProblem;

import java.util.List;

/**
 * The <tt>MaximumMatchingAlgorithm</tt> class provides a skeletal implementation for different algorithms that computes
 * the maximum cardinality matching in a bipartite graph.
 */
public abstract class MaximumMatchingAlgorithm extends GraphAlgorithm {
    public MaximumMatchingAlgorithm(MatchingProblem matchingProblem) {
        super(matchingProblem);
    }

    public abstract List<Match> getMaxMatching();
}
