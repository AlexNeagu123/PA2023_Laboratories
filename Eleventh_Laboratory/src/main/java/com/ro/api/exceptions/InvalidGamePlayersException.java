package com.ro.api.exceptions;

public class InvalidGamePlayersException extends RuntimeException {
    public InvalidGamePlayersException() {
        super("The player id's should be different");
    }
}
