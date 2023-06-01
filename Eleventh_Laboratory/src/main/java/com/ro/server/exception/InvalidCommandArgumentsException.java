package com.ro.server.exception;

public class InvalidCommandArgumentsException extends GameException {
    public InvalidCommandArgumentsException(String commandName, int expected, int actual) {
        super(String.format("Invalid number of arguments for command '%s'. %d expected but %d found.", commandName, expected, actual));
    }

    public InvalidCommandArgumentsException(String commandName, String message) {
        super(String.format("Invalid arguments for command '%s': %s.", commandName, message));
    }
}

