package com.graph;

import com.entities.Company;
import com.entities.Person;

import java.util.List;

/**
 * The <tt>Node</tt> interface represents the elements from a social network.
 * <p>
 * An element from the social network is either a {@link Person} or a {@link Company}.
 * <p>
 * Each <tt>Person</tt> might have a well-defined relationship with another <tt>Person</tt> or <tt>Company</tt>.
 * <p>
 * Thus, a social network might be viewed as an undirected graph, were the nodes have edges (relationships) between
 * each other.
 * <p>
 * Each <tt>Node</tt> is uniquely identified by its name (the name of a person or a company) that might be extracted using the
 * {@code getName()} method.
 * <p>
 * A specific <tt>Node</tt> have edges (relationships) to other nodes that might be called <b>neighbours</b>. All the neighbours
 * of a given node might be extracted using the {@code getNeighbours()} method.
 */
public interface Node {
    /**
     * @return The name of the node (uniquely identified)
     */
    String getName();

    /**
     * @return The list of neighbours of the node
     */
    List<Node> getNeighbours();

}
