package ro.server.exception;

public class InvalidTurnException extends GameException {
    public InvalidTurnException() {
        super("You can't make a move at the moment. Pleaser wait your opponent to move!");
    }
}
