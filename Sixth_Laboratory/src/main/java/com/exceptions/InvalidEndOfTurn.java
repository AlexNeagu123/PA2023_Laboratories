package com.exceptions;

public class InvalidEndOfTurn extends Exception {
    public InvalidEndOfTurn() {
        super("The current player already colored the selected line");
    }
}
