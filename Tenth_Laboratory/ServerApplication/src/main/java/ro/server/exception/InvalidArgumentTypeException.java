package ro.server.exception;

public class InvalidArgumentTypeException extends GameException {
    public InvalidArgumentTypeException() {
        super("Please, enter some valid integers as coordinates");
    }
}
