package ro.server.exception;

public class NonExistentCommand extends GameException {
    public NonExistentCommand(String commandName) {
        super("'" + commandName + "' command doesn't exist!");
    }
}
