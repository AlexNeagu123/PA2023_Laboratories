package com.ro.server.exception;

public class InvalidCoordinatesException extends GameException {
    public InvalidCoordinatesException() {
        super("The coordinates you have entered are outside the board. Please respect the [0,14] x [0,14] square");
    }
}
