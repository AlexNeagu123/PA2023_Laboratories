package com.problem;

import com.graph.Node;
import lombok.ToString;

import java.util.*;

/**
 * The <tt>MatchingProblem</tt> class has the role of storing all the relevant information about an instance of
 * student-project allocation problem.
 * <p>
 * This class stores a bipartite graph where each {@link Node} is either a
 * {@link com.graph.Project} or a {@link com.graph.Student} object.
 * <p>
 * It's forbidden to exist an edge between two <tt>Student</tt> nodes.
 * <p>
 * It's forbidden to exist an edge between two <tt>Project</tt> nodes.
 * <p>
 * A matching problem might be visualised as two sets of <pp>Node</pp> objects, one on the left side and the other on the right side.
 * <p>
 * The nodes on the left side are all <tt>Student</tt> objects and the nodes on the right side are all <tt>Project</tt> objects.
 * <p>
 * An edge of two nodes exists only if the nodes are on <b>different sides</b>.
 */
@ToString
public class MatchingProblem {
    private final Set<Node> leftSide;
    private final Set<Node> rightSide;
    private final Map<Node, Set<Node>> neighbours;

    /**
     * @param matchingProblemBuilder A <tt>MatchingProblemBuilder</tt> object that stores all the relevant data used to
     *                               construct the <tt>MatchingProblem</tt> object
     */
    MatchingProblem(MatchingProblemBuilder matchingProblemBuilder) {
        this.leftSide = matchingProblemBuilder.leftSide;
        this.rightSide = matchingProblemBuilder.rightSide;
        neighbours = new HashMap<>();
        System.out.println("Created a Student-Project allocation problem with " + this.leftSide.size() + " students and " + this.rightSide.size() + " projects.");
    }

    /**
     * @return The {@link Set} of nodes from the left side (<tt>Students</tt>)
     */
    public Set<Node> getLeftSide() {
        return new HashSet<>(leftSide);
    }

    /**
     * @return The {@link Set} of nodes from the right side (<tt>Projects</tt>)
     */
    public Set<Node> getRightSide() {
        return new HashSet<>(rightSide);
    }

    /**
     * @return A {@link Set} of all the nodes in the bipartite graph
     */
    public Set<Node> getAllNodes() {
        Set<Node> allNodes = new HashSet<>(leftSide);
        allNodes.addAll(rightSide);
        return allNodes;
    }

    /**
     * @param node A {@link Node} object
     * @return A list of all the neighbours of {@code node}
     */
    public List<Node> getNeighboursOfNode(Node node) {
        if (neighbours.get(node) == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(neighbours.get(node));
    }

    /**
     * Add a {@link Node} (<tt>Student</tt>) to the left side of the bipartite graph.
     *
     * @param node The node to be added
     */
    void addToLeftSide(Node node) {
        if (!rightSide.contains(node)) {
            leftSide.add(node);
        }
    }

    /**
     * Add a {@link Node} (<tt>Project</tt>) to the right side of the bipartite graph.
     *
     * @param node The node to be added
     */
    public void addToRightSide(Node node) {
        if (!leftSide.contains(node)) {
            rightSide.add(node);
        }
    }

    /**
     * Add an edge between two nodes in the bipartite graph.
     * <p>
     * If the first node isn't on the <b>left side</b> or the second node isn't on the <b>right side</b>,
     * nothing happens (silent behaviour). Otherwise, the edge is successfully added.
     *
     * @param leftNode  A {@link Node} from the left side of the bipartite graph (<tt>Student</tt>)
     * @param rightNode A {@link Node} from the right side of the bipartite graph (<tt>Project</tt>)
     */
    public void addEdge(Node leftNode, Node rightNode) {
        if (!leftSide.contains(leftNode) || !rightSide.contains(rightNode)) {
            return;
        }
        neighbours.computeIfAbsent(leftNode, k -> new HashSet<>());
        neighbours.computeIfAbsent(rightNode, k -> new HashSet<>());
        neighbours.get(leftNode).add(rightNode);
        neighbours.get(rightNode).add(leftNode);
    }

    /**
     * Prints all the students (nodes from the left side) that have a number of preferences (edges)
     * lower than the average number of preferences.
     */
    public void printSpecialStudents() {
        System.out.println("Students that have the number of preferences lower than the average number of preferences: ");
        final double average = leftSide.stream().filter(s -> neighbours.get(s) != null)
                .mapToInt(s -> neighbours.get(s).size())
                .average()
                .orElseThrow(IllegalArgumentException::new);

        leftSide.stream().filter(s -> neighbours.get(s) != null)
                .filter(s -> neighbours.get(s).size() < average)
                .forEach(System.out::println);
    }

    /**
     * Printed the students (nodes from the left side) and the projects (nodes from the right side) ordered by names.
     */
    public void printEntitiesSortedByNames() {
        System.out.println("All the students printed in sorted order by names: ");
        this.leftSide.stream().sorted().forEach(System.out::println);
        System.out.println("All the projects printed in sorted order by names: ");
        this.rightSide.stream().sorted().forEach(System.out::println);
    }

    public static MatchingProblemBuilder builder() {
        return new MatchingProblemBuilder();
    }

    /**
     * The <tt>MatchingProblemBuilder</tt> class collects relevant information about a {@link MatchingProblem} object
     * such as the nodes from the left side and the nodes from the right side and calls the <tt>MatchingProblem</tt>
     * constructor via the {@code build()} method.
     */
    public static class MatchingProblemBuilder {
        Set<Node> leftSide;
        Set<Node> rightSide;

        public MatchingProblemBuilder() {
            leftSide = new HashSet<>();
            rightSide = new HashSet<>();
        }

        public MatchingProblemBuilder leftSide(List<Node> leftSide) {
            this.leftSide = new HashSet<>(leftSide);
            return this;
        }

        public MatchingProblemBuilder rightSide(List<Node> rightSide) {
            this.rightSide = new HashSet<>(rightSide);
            return this;
        }

        public MatchingProblem build() {
            return new MatchingProblem(this);
        }
    }
}

