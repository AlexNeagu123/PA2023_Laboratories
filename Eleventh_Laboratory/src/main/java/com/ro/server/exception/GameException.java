package com.ro.server.exception;

public abstract class GameException extends Exception {
    public GameException(String msg) {
        super(msg);
    }
}
