package ro.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ro.commands.*;
import ro.exceptions.*;
import ro.game.explorations.Exploration;
import ro.players.Robot;

import java.util.Arrays;

/**
 * The <tt>CommandParser</tt> class is responsible for parsing a line of text introduced by the user and map it with a
 * valid {@link Command}.
 * <p>
 * Syntactic and semantic errors are also caught by the methods provided in this class and signaled to the user.
 */
@RequiredArgsConstructor
public class CommandParser {
    @Getter
    private final Exploration exploration;

    /**
     * Validates if the {@link AddCommand} has the correct number of arguments, is executed at the right time,
     * and the name of the robot is unique.
     *
     * @param arguments The command arguments that have been introduced by the user
     * @return The name of the newly added robot
     * @throws InvalidCommandArgumentsException If more than one command arguments have been introduced
     *                                          or if no arguments have been introduced
     * @throws CommandNotAvailableException     If the exploration process already have begun
     */
    private String checkAddCommandAndExtractName(String[] arguments) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 1) {
            throw new InvalidCommandArgumentsException("add", 1, arguments.length);
        }

        if (exploration.getState() != 0) {
            throw new CommandNotAvailableException("add");
        }

        if (!exploration.hasUniqueName(arguments[0])) {
            throw new InvalidCommandArgumentsException("add", String.format("A robot with the name '%s' already exists!", arguments[0]));
        }
        return arguments[0];
    }

    /**
     * Validates if the {@link StartCommand} has the correct number of arguments and is executed at the right time.
     *
     * @param arguments The command arguments that have been introduced by the user
     * @throws InvalidCommandArgumentsException If more than zero command arguments have been introduced
     * @throws CommandNotAvailableException     If the exploration process already have begun
     */
    private void checkStartCommand(String[] arguments) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 0) {
            throw new InvalidCommandArgumentsException("start", 0, arguments.length);
        }
        if (exploration.getState() != 0) {
            throw new CommandNotAvailableException("start");
        }
    }

    /**
     * Validates if the {@link OffCommand} has the correct number of arguments, is executed at the right time,
     * and the robot with the introduced name exists in the exploration.
     *
     * @param arguments The command arguments that have been introduced by the user
     * @return The name of the robot to be paused
     * @throws InvalidCommandArgumentsException If more than one command arguments have been introduced
     *                                          or if no arguments have been introduced
     * @throws CommandNotAvailableException     If the exploration process have not yet begun
     */
    private String checkOffCommandAndExtractName(String[] arguments, Exploration exploration) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 1) {
            throw new InvalidCommandArgumentsException("off", 1, arguments.length);
        }
        if (exploration.getState() != 1) {
            throw new CommandNotAvailableException("off");
        }
        if (exploration.hasUniqueName(arguments[0])) {
            throw new InvalidCommandArgumentsException("off", String.format("A robot with the name '%s' doesn't exist!", arguments[0]));
        }
        return arguments[0];
    }

    /**
     * Validates if the {@link OnCommand} has the correct number of arguments, is executed at the right time,
     * and the robot with the introduced name exists in the exploration.
     *
     * @param arguments The command arguments that have been introduced by the user
     * @return The name of the robot to be resumed
     * @throws InvalidCommandArgumentsException If more than one command arguments have been introduced
     *                                          or if no arguments have been introduced
     * @throws CommandNotAvailableException     If the exploration process have not yet begun
     */
    private String checkOnCommandAndExtractName(String[] arguments, Exploration exploration) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 1) {
            throw new InvalidCommandArgumentsException("on", 1, arguments.length);
        }
        if (exploration.getState() != 1) {
            throw new CommandNotAvailableException("on");
        }
        if (exploration.hasUniqueName(arguments[0])) {
            throw new InvalidCommandArgumentsException("on", String.format("A robot with the name '%s' doesn't exist!", arguments[0]));
        }
        return arguments[0];
    }

    /**
     * Validates if the {@link OffAllCommand} has the correct number of arguments and is executed at the right time.
     *
     * @param arguments The command arguments that have been introduced by the user
     * @throws InvalidCommandArgumentsException If more than zero command arguments have been introduced
     * @throws CommandNotAvailableException     If the exploration process have not yet begun
     */
    private void checkOffAllCommand(String[] arguments) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 0) {
            throw new InvalidCommandArgumentsException("off-all", 0, arguments.length);
        }
        if (exploration.getState() != 1) {
            throw new CommandNotAvailableException("off-all");
        }
    }

    /**
     * Validates if the {@link OnAllCommand} has the correct number of arguments and is executed at the right time.
     *
     * @param arguments The command arguments that have been introduced by the user
     * @throws InvalidCommandArgumentsException If more than zero command arguments have been introduced
     * @throws CommandNotAvailableException     If the exploration process have not yet begun
     */
    private void checkOnAllCommand(String[] arguments) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 0) {
            throw new InvalidCommandArgumentsException("on-all", 0, arguments.length);
        }
        if (exploration.getState() != 1) {
            throw new CommandNotAvailableException("on-all");
        }
    }

    private void checkQuitCommand(String[] arguments) throws InvalidCommandArgumentsException, CommandNotAvailableException {
        if (arguments.length != 0) {
            throw new InvalidCommandArgumentsException("quit", 0, arguments.length);
        }
    }

    /**
     * Check if a command is valid and maps it to a {@link Command} object
     *
     * @param commandName The name of the command
     * @param arguments   A list of the command arguments
     * @return The {@link Command} object that has been mapped
     * @throws CommandNotAvailableException     If the command can't be executed at this moment of time
     * @throws InvalidCommandArgumentsException If the number of arguments introduced is incorrect
     * @throws NoMoreFreeCoordinates            If no more robots can be added in the exploration
     * @throws NonExistentCommand               If the {@code commandName} is not a valid command name
     */
    public Command matchAndBuildCommand(String commandName, String[] arguments) throws CommandNotAvailableException, InvalidCommandArgumentsException, NoMoreFreeCoordinates, NonExistentCommand {
        if (commandName.equals("add")) {
            String robotName = checkAddCommandAndExtractName(arguments);
            return new AddCommand(new Robot(robotName, exploration));
        }

        if (commandName.equals("quit")) {
            checkQuitCommand(arguments);
            return new QuitCommand();
        }

        if (commandName.equals("start")) {
            checkStartCommand(arguments);
            return new StartCommand(exploration);
        }

        if (commandName.equals("off")) {
            String robotName = checkOffCommandAndExtractName(arguments, exploration);
            return new OffCommand(robotName, exploration);
        }

        if (commandName.equals("on")) {
            String robotName = checkOnCommandAndExtractName(arguments, exploration);
            return new OnCommand(robotName, exploration);
        }

        if (commandName.equals("off-all")) {
            checkOffAllCommand(arguments);
            return new OffAllCommand(exploration);
        }

        if (commandName.equals("on-all")) {
            checkOnAllCommand(arguments);
            return new OnAllCommand(exploration);
        }
        throw new NonExistentCommand(commandName);
    }

    public Command parseCommand(String commandText) throws CommandNotAvailableException, InvalidCommandArgumentsException, NoMoreFreeCoordinates, NonExistentCommand {
        String[] splitCommand = commandText.split("\\s+");
        return matchAndBuildCommand(splitCommand[0], Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
    }
}