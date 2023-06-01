package com.ro.server.exception;

public class GameConnectionException extends GameException {
    public GameConnectionException() {
        super("Unfortunately, the connection with the game couldn't be completed successfully.");
    }
}
