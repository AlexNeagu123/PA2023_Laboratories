package com.entities;

import com.graph.Node;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The <tt>Company</tt> class defines a company that may have multiple employers. Each company is identifiable by a unique name.
 * <p>
 * Two <tt>Company</tt> objects are compared by their names.
 * <p>
 * There might be several {@link Person} objects that have a relationship with this <tt>Company</tt>.
 */
@EqualsAndHashCode
public class Company implements Node, Comparable<Company> {
    @Setter
    @Getter
    private String name;
    @EqualsAndHashCode.Exclude
    private final List<Node> personRelationships = new ArrayList<>();

    public Company(String name) {
        this.name = name;
    }

    /**
     * Adds a {@link Person} object into a local list of {@link Node} objects.
     * <p>
     * Each <tt>Person</tt> object added to the list, have a specific relationship with this <tt>Company</tt>.
     * <p>
     * In other words, all the <tt>Person</tt> objects that has a relationship with this <tt>Company</tt> might be viewed
     * as <b>neighbours</b> in the graph associated to the social network.
     *
     * @param person The newly added <tt>Person</tt>
     */
    void addPersonRelationship(Person person) {
        personRelationships.add(person);
    }

    /**
     * @return A list of all the {@link Node} objects that have a relationship with this <tt>Company</tt>
     */
    @Override
    public List<Node> getNeighbours() {
        return new ArrayList<>(personRelationships);
    }

    @Override
    public int compareTo(@NonNull Company o) {
        return name.compareTo(o.name);
    }
}
