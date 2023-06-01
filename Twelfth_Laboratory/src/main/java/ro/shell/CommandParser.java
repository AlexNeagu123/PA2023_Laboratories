package ro.shell;

import lombok.RequiredArgsConstructor;
import ro.command.*;
import ro.exception.InvalidCommandArguments;
import ro.exception.InvalidSystemPath;
import ro.exception.NonExistentCommand;

import java.util.Arrays;

/**
 * The <tt>CommandParser</tt> class is responsible for parsing a line of text introduced by the user and map it with a
 * valid {@link Command}.
 * <p>
 * Syntactic and semantic errors are also caught by the methods provided in this class and signaled to the user.
 * <p>
 * The following commands are supported:
 * <ul>
 *     <li>
 *         <b>inspect-class</b> <i>path of the class</i>: Implemented for the <b>Compulsory</b> task
 *     </li>
 *     <li>
 *         <b>inspect-dir</b> <i>path of the directory</i>: Implemented for the <b>Homework</b> task
 *     </li>
 *     <li>
 *         <b>inspect-jar</b> <i>path of the .jar file</i>: Implemented for the <b>Homework</b> task
 *     </li>
 *     <li>
 *         <b>inspect-source</b> <i>path of .java file</i>: Implemented for the <b>Bonus</b> task
 *     </li>
 * </ul>
 */
@RequiredArgsConstructor
public class CommandParser {
    private String checkInspectClassCommandAndReturnPath(String[] arguments) throws InvalidCommandArguments {
        if (arguments.length != 1) {
            throw new InvalidCommandArguments("inspect-class", 1, arguments.length);
        }
        return arguments[0];
    }

    private String checkInspectDirCommandAndReturnPath(String[] arguments) throws InvalidCommandArguments {
        if (arguments.length != 1) {
            throw new InvalidCommandArguments("inspect-dir", 1, arguments.length);
        }
        return arguments[0];
    }

    private String checkInspectJarCommandAndReturnPath(String[] arguments) throws InvalidCommandArguments {
        if (arguments.length != 1) {
            throw new InvalidCommandArguments("inspect-jar", 1, arguments.length);
        }
        return arguments[0];
    }

    private String checkInspectSourceCommandAndReturnPath(String[] arguments) throws InvalidCommandArguments {
        if (arguments.length != 1) {
            throw new InvalidCommandArguments("inspect-source", 1, arguments.length);
        }
        return arguments[0];
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
        if (commandName.equals("inspect-class")) {
            String classPath = checkInspectClassCommandAndReturnPath(arguments);
            return new InspectClassCommand(classPath);
        }
        if (commandName.equals("inspect-dir")) {
            String dirPath = checkInspectDirCommandAndReturnPath(arguments);
            return new InspectDirCommand(dirPath);
        }
        if (commandName.equals("inspect-jar")) {
            String jarPath = checkInspectJarCommandAndReturnPath(arguments);
            return new InspectJarCommand(jarPath);
        }
        if (commandName.equals("inspect-source")) {
            String sourcePath = checkInspectSourceCommandAndReturnPath(arguments);
            return new InspectSourceCommand(sourcePath);
        }
        throw new NonExistentCommand(commandName);
    }

    public Command parseCommand(String commandText) throws NonExistentCommand, InvalidCommandArguments, InvalidSystemPath {
        String[] splitCommand = commandText.split("\\s+");
        return matchAndBuildCommand(splitCommand[0], Arrays.copyOfRange(splitCommand, 1, splitCommand.length));
    }
}