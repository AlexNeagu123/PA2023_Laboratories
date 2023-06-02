package ro.shell;

import lombok.RequiredArgsConstructor;
import ro.command.Command;
import ro.command.DisplayLocalesCommand;
import ro.command.InfoCommand;
import ro.command.SetLocaleCommand;
import ro.exception.InvalidCommandArguments;
import ro.exception.NonExistentCommand;

import java.util.Arrays;

@RequiredArgsConstructor
public class CommandParser {
    private void checkDisplayLocales(String[] arguments) throws InvalidCommandArguments {
        if (arguments.length != 0) {
            throw new InvalidCommandArguments("display-locales", 0, arguments.length);
        }
    }

    private String checkSetLocaleAndReturnTag(String[] arguments) throws InvalidCommandArguments {
        if (arguments.length != 1) {
            throw new InvalidCommandArguments("set-locale", 1, arguments.length);
        }
        return arguments[0];
    }

    private void checkInfoCommand(String[] arguments) throws InvalidCommandArguments {
        if (arguments.length != 0) {
            throw new InvalidCommandArguments("info", 0, arguments.length);
        }
    }

    /**
     * Check if a command is valid and maps it to a {@link Command} object
     *
     * @param commandName The name of the command
     * @param arguments   A list of the command arguments
     * @return The {@link Command} object that has been mapped
     * @throws NonExistentCommand If the {@code commandName} is not a valid command name
     */
    public Command matchAndBuildCommand(String commandName, String[] arguments) throws InvalidCommandArguments, NonExistentCommand {
        if (commandName.equals("display-locales")) {
            checkDisplayLocales(arguments);
            return new DisplayLocalesCommand();
        }
        if (commandName.equals("set-locale")) {
            String tag = checkSetLocaleAndReturnTag(arguments);
            return new SetLocaleCommand(tag);
        }
        if (commandName.equals("info")) {
            checkInfoCommand(arguments);
            return new InfoCommand();
        }
        throw new NonExistentCommand(commandName);
    }

    public Command parseCommand(String commandText) throws NonExistentCommand, InvalidCommandArguments {
        String[] splitCommand = commandText.split("\\s+");
        return matchAndBuildCommand(splitCommand[0], Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
    }
}