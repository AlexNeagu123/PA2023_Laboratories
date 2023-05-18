package ro.server.exception;

public class PlayerUnknownException extends GameException {
    public PlayerUnknownException(String command) {
        super("Oops. '" + command + "' command can't be executed if your name is not set. Please check the 'set-name' command.");
    }
}
