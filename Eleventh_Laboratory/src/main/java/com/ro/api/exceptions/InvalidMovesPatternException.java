package com.ro.api.exceptions;

public class InvalidMovesPatternException extends RuntimeException {
    public InvalidMovesPatternException() {
        super("The moves pattern is not correct! It should be a sequence of 'm1 m2 {p1/p2}' where m1 and m2 are between 0 and 14");
    }
}
