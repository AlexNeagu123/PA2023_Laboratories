package com.entities;

import com.graph.Node;
import lombok.*;

import java.util.*;

/**
 * The <tt>Person</tt> class defines a person that works at a company. Each person is identifiable by a unique name.
 * <p>
 * Two <tt>Person</tt> objects are comparable by their names.
 * <p>
 * A <tt>Person</tt> might have specific relationships with other persons or companies.
 * <p>
 * There might be some specific types of a <tt>Person</tt> such as {@link Programmer} and {@link Designer}.
 */
@EqualsAndHashCode
@ToString(exclude = "relationships")
public class Person implements Node, Comparable<Person> {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private Date birthDate;

    @EqualsAndHashCode.Exclude
    private final Map<Node, String> relationships = new HashMap<>();

    /**
     * Constructs a <tt>Person</tt> that is uniquely defined by a name and has a specified birth date.
     *
     * @param name The name of the newly created <tt>Person</tt>
     * @param birthDate The date of birth of the newly created <tt>Person</tt>
     */
    public Person(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    /**
     * A relationship between this <tt>Person</tt> and other {@link Node} in the social network is created.
     * <p>
     * Because the social network is viewed as an undirected graph, the same relationship is created the other way around.
     * <p>
     * If the relationship is of type {@code Person} to {@code Person}, the same method is called for the other <tt>Person</tt>
     * object in the relationship only if this relationship doesn't already exist the other way around.
     * <p>
     * If the relationship is of type <tt>Person</tt> to <tt>Company</tt>, the {@link Company} object adds this <tt>Person</tt>
     * to its own list of {@code Persons} with specific relationships to it.
     *
     * @param node  The {@link Node} that has a relationship with this <tt>Person</tt>
     * @param value A specific description of the relationship
     */
    public void addRelationships(Node node, String value) {
        relationships.put(node, value);
        if (node instanceof Company) {
            ((Company) node).addPersonRelationship(this);
        } else if (node instanceof Person) {
            Person person = (Person) node;
            if (!person.containsRelationship(this)) {
                person.addRelationships(this, value);
            }
        }
    }

    /**
     * Checks if this <tt>Person</tt> has a relationship with a given {@link Node}
     *
     * @param checkedNode The {@code Node} to check
     * @return <tt>true</tt> if this {@code Person} has a relationship with {@code checkedNode} and <tt>false</tt> otherwise
     */
    private boolean containsRelationship(Node checkedNode) {
        return relationships.containsKey(checkedNode);
    }

    /**
     * @return A list of all the {@link Node} objects that have a relationship with this <tt>Person</tt>
     */
    @Override
    public List<Node> getNeighbours() {
        return new ArrayList<>(relationships.keySet());
    }

    @Override
    public int compareTo(@NonNull Person o) {
        return name.compareTo(o.name);
    }
}
