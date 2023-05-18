package ro.server.exception;

public class PlayerNotInGameException extends GameException {
    public PlayerNotInGameException(String commandName) {
        super(String.format("Oops! The %s command can be executed only if you are in a game.", commandName));
    }
}
