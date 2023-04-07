package com.graph;

import com.github.javafaker.Faker;
import lombok.ToString;

/**
 * The <tt>Project</tt> class defines a special type of {@link Node} in the student-project allocation problem.
 */
@ToString(callSuper = true)
public class Project extends Node {
    /**
     * Constructs a <tt>Project</tt> that is uniquely identified by a specified name.
     *
     * @param name The name of the project
     */
    public Project(String name) {
        super(name);
    }

    /**
     * Constructs a <tt>Project</tt> that is uniquely identified by a name.
     * The name is randomly generated using {@link Faker}
     */
    public Project() {
        Faker faker = new Faker();
        this.name = faker.company().catchPhrase();
    }
}
