package ro.server.exception;

public class PlayerInGameException extends GameException {
    public PlayerInGameException(String command) {
        super("'" + command + "' command can't be executed while you are playing a game.");
    }
}
