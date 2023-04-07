package com.graph;

import lombok.Getter;
import lombok.Setter;

/**
 * The <tt>Match</tt> class represents a pair of nodes that consists of a {@link Student} and a {@link Project}.
 * Multiple disjoint <tt>Match</tt> elements form a bipartite matching
 */
@Getter
@Setter
public class Match {
    private Node student;
    private Node project;

    public Match(Node student, Node project) {
        this.student = student;
        this.project = project;
    }

    @Override
    public String toString() {
        return "Match{" + student.getName() + " - " + project.getName() + "}";
    }
}
