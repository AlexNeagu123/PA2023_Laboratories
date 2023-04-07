package com.exceptions;

public class InvalidEndpoints extends Exception {
    public InvalidEndpoints() {
        super("The endpoints of the road should be different!");
    }
}
