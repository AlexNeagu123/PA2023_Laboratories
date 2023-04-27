package ro.exceptions;

public class CommandNotAvailableException extends Exception {
    public CommandNotAvailableException(String commandName) {
        super(String.format("You can't execute the '%s' command right now!", commandName));
    }
}
