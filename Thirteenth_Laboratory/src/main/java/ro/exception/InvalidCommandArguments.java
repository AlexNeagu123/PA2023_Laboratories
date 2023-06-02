package ro.exception;

public class InvalidCommandArguments extends Exception {
    public InvalidCommandArguments(String commandName, int expected, int actual) {
        super(String.format("Invalid number of arguments for command '%s'. %d expected but %d found.", commandName, expected, actual));
    }
}
