package com.exceptions;

public class InvalidProblem extends Exception {
    public InvalidProblem() {
        super("The instance of the problem in invalid. All road endpoints should be a location found in the problem!");
    }
}
