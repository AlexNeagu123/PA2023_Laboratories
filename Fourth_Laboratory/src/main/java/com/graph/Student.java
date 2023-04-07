package com.graph;

import com.github.javafaker.Faker;
import lombok.ToString;

/**
 * The <tt>Project</tt> class defines a special type of {@link Node} in the student-project allocation problem.
 */
@ToString(callSuper = true)
public class Student extends Node {
    /**
     * Constructs a <tt>Student</tt> that is uniquely identified by a specified name.
     *
     * @param name The name of the student
     */
    public Student(String name) {
        super(name);
    }

    /**
     * Constructs a <tt>Student</tt> that is uniquely identified by a name.
     * The name is randomly generated using {@link Faker}
     */
    public Student() {
        Faker faker = new Faker();
        this.name = faker.name().fullName();
    }
}
