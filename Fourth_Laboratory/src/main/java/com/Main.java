package com;

import com.algorithms.Graph4jMaximumMatching;
import com.algorithms.GreedyMaximumMatching;
import com.algorithms.JgraphtMaximumMatching;
import com.generators.MatchingProblemGenerator;
import com.graph.Match;
import com.problem.MatchingProblem;

import java.util.List;

/**
 * A random {@link MatchingProblem} instance is created.
 * <p>
 * All the students and projects are printed on the screen, sorted by their names.
 * <p>
 * All the students with a number of preferences smaller than the average are printed on the screen.
 * <p>
 * The maximum cardinality bipartite matching problem is solved with 3 different implementations
 * <p>
 * 1. A greedy algorithm that doesn't always find the optimal answer.
 * <p>
 * 2. Hopcroft Karp algorithm implemented in the JGraphT library that always find the optimal answer.
 * <p>
 * 3. Hopcroft Karp algorithm implemented in the Graph4J library that always find the optimal answer.
 * <p>
 * Relevant statistics and results are printed on the screen for each algorithm.
 */
public class Main {
    public static void main(String[] args) {
        MatchingProblem matchingProblem = MatchingProblemGenerator.generateMatchingProblem(50, 50, 100);
        matchingProblem.printEntitiesSortedByNames();
        matchingProblem.printSpecialStudents();
        computeGreedyMaxMatching(matchingProblem);
        computeJgraphtMaxMatching(matchingProblem);
        computeGraph4jMaxMatching(matchingProblem);
    }

    public static void computeGreedyMaxMatching(MatchingProblem matchingProblem) {
        long startTime = System.currentTimeMillis();
        System.out.println("Computing the maximum cardinality bipartite matching with a greedy algorithm");
        GreedyMaximumMatching greedyMaxMatching = new GreedyMaximumMatching(matchingProblem);
        List<Match> maxMatching = greedyMaxMatching.getMaxMatching();
        System.out.println("The cardinality of the matching: " + maxMatching.size());
        System.out.println("Students and Projects matched with each other: ");
        System.out.println(maxMatching);
        long endTime = System.currentTimeMillis();
        System.out.println("Time of running: " + (endTime - startTime) + "ms");
    }

    public static void computeJgraphtMaxMatching(MatchingProblem matchingProblem) {
        long startTime = System.currentTimeMillis();
        System.out.println("Computing the maximum cardinality bipartite matching with the Hopcroft Karp algorithm implemented by the Jgrapht library");
        JgraphtMaximumMatching jgraphtMaxMatching = new JgraphtMaximumMatching(matchingProblem);
        List<Match> maxMatching = jgraphtMaxMatching.getMaxMatching();
        System.out.println("The cardinality of the matching: " + maxMatching.size());
        System.out.println("Students and Projects matched with each other: ");
        System.out.println(maxMatching);
        long endTime = System.currentTimeMillis();
        System.out.println("Time of running: " + (endTime - startTime) + "ms");
    }

    public static void computeGraph4jMaxMatching(MatchingProblem matchingProblem) {
        long startTime = System.currentTimeMillis();
        System.out.println("Computing the maximum cardinality bipartite matching with the Hopcroft Karp algorithm implemented by the Graph4j library");
        Graph4jMaximumMatching graph4jMaxMatching = new Graph4jMaximumMatching(matchingProblem);
        List<Match> maxMatching = graph4jMaxMatching.getMaxMatching();
        System.out.println("The cardinality of the matching: " + maxMatching.size());
        System.out.println("Students and Projects matched with each other: ");
        System.out.println(maxMatching);
        long endTime = System.currentTimeMillis();
        System.out.println("Time of running: " + (endTime - startTime) + "ms");
    }
}