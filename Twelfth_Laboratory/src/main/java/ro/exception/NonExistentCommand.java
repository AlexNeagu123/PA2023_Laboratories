package ro.exception;

public class NonExistentCommand extends Exception {
    public NonExistentCommand(String commandName) {
        super(commandName + " command doesn't exist!");
    }
}
