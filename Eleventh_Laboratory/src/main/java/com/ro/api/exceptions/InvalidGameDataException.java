package com.ro.api.exceptions;

public class InvalidGameDataException extends RuntimeException {
    public InvalidGameDataException() {
        super("The 'winner' field should be either 'p1' or 'p2'!");
    }
}
