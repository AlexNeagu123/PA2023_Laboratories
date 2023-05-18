package ro.server.exception;

public class NoAvailableGamesException extends GameException {
    public NoAvailableGamesException() {
        super("There are no available game rooms at the moment. Please try later.");
    }
}
