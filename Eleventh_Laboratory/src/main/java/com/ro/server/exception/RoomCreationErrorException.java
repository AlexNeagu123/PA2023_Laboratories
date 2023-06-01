package com.ro.server.exception;

public class RoomCreationErrorException extends GameException {
    public RoomCreationErrorException() {
        super("Unfortunately, there was an error while maintaining you room. Please create another one.");
    }
}
