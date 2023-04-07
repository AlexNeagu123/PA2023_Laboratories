package com.graph;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * The <tt>Node</tt> class represents either a {@link Student} or a {@link Project} object in the student-project allocation problem.
 * <p>
 * Each <tt>Student</tt> might have multiple <tt>Projects</tt> as preferences.
 * <p>
 * Each <tt>Project</tt> might be preferred by multiple <tt>Students</tt>
 * <p>
 * Each <tt>Node</tt> in the student-project allocation problem is identifiable by a unique name.
 */
@ToString
@EqualsAndHashCode
public abstract class Node implements Comparable<Node> {
    String name;

    public Node(String name) {
        this.name = name;
    }

    public Node() {
    }

    /**
     * @return The name of the node (uniquely identified)
     */
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(@NonNull Node o) {
        return name.compareTo(o.name);
    }

}
