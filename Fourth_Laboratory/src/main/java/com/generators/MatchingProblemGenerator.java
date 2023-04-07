package com.generators;

import com.graph.Node;
import com.graph.Project;
import com.graph.Student;
import com.problem.MatchingProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class is able to generate random instances of a {@link MatchingProblem}
 */
public class MatchingProblemGenerator {
    /**
     * Generate a random instance of a {@link MatchingProblem} considering specific parameters
     *
     * @param leftSideCount  The number of nodes from the left side of the bipartite graph
     * @param rightSideCount The number of nodes from the right side of the bipartite graph
     * @param edgeCount      The number of edges in the bipartite graph
     * @return A randomly created student-project allocation problem
     */
    public static MatchingProblem generateMatchingProblem(int leftSideCount, int rightSideCount, int edgeCount) {
        List<Node> students = new LinkedList<>(Arrays.asList(IntStream.rangeClosed(0, leftSideCount - 1)
                .mapToObj(i -> new Student())
                .toArray(Node[]::new)));

        List<Node> projects = new ArrayList<>(Arrays.asList(IntStream.rangeClosed(0, rightSideCount - 1)
                .mapToObj(i -> new Project())
                .toArray(Node[]::new)));

        MatchingProblem matchingProblem = MatchingProblem
                .builder()
                .leftSide(students)
                .rightSide(projects)
                .build();

        for (int i = 0; i < edgeCount; ++i) {
            int studentIndex = (int) (Math.random() * (leftSideCount - 1));
            int projectIndex = (int) (Math.random() * (rightSideCount - 1));
            matchingProblem.addEdge(students.get(studentIndex), projects.get(projectIndex));
        }

        return matchingProblem;
    }
}
