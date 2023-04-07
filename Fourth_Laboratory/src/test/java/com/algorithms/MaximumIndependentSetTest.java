package com.algorithms;

import com.graph.Node;
import com.graph.Project;
import com.graph.Student;
import com.problem.MatchingProblem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class MaximumIndependentSetTest {
    @Test
    void shouldReturnFourNodes_whenBipartiteGraphWithThreeNodesInBothSidesOneNodeWithDegreeZeroAndFiveNodesWithDegreeTwo() {
        List<Node> leftSide = new ArrayList<>(Arrays.asList(IntStream.rangeClosed(0, 2)
                .mapToObj(i -> new Student())
                .toArray(Node[]::new)));

        List<Node> rightSide = new ArrayList<>(Arrays.asList(IntStream.rangeClosed(0, 2)
                .mapToObj(i -> new Project())
                .toArray(Node[]::new)));

        MatchingProblem matchingProblem = MatchingProblem.builder().leftSide(leftSide).rightSide(rightSide).build();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (i == j || i == 2) {
                    continue;
                }
                matchingProblem.addEdge(leftSide.get(i), rightSide.get(j));
            }
        }
        MaximumIndependentSet maximumIndependentSet = new MaximumIndependentSet(matchingProblem);
        Assertions.assertEquals(maximumIndependentSet.getMaximumIndependentSet().size(), 4);
    }
}