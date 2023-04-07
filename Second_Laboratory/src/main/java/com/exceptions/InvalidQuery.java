package com.exceptions;

public class InvalidQuery extends Exception {
    public InvalidQuery() {
        super("Starting and ending locations should be found in problem's locations list!");
    }
}
