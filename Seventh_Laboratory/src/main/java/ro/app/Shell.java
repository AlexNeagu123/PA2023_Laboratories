package ro.app;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.commands.Command;
import ro.exceptions.CommandNotAvailableException;
import ro.exceptions.InvalidCommandArgumentsException;
import ro.exceptions.NoMoreFreeCoordinates;
import ro.exceptions.NonExistentCommand;
import ro.utils.CommandParser;
import ro.utils.CommandScanner;

import java.io.IOException;

/**
 * The <tt>Shell</tt> class interacts with the user through the command line until the end of the exploration.
 * <p>
 * Lines of text are repeatedly read, mapped to a {@link Command} object, and the executed
 */
@AllArgsConstructor
@Log4j2
public class Shell {
    private final CommandScanner commandScanner;
    private final CommandParser commandParser;

    public void execute() {
        System.out.println("Firstly, you can add some robots on the map by typing several (maybe 0) 'add <name>' commands. " +
                "The starting point of the robot is arbitrary chosen.");
        System.out.println("After you have added the desired number of robots, press 'start' in order to start the exploration.");
        System.out.println("During the exploration you can use the following commands: 'on <name>', 'off <name>', 'on-all', " +
                "'off-all' in order to pause/resume the exploration of the robots.");
        while (true) {
            try {
                while (System.in.available() <= 0) {
                    if (commandParser.getExploration().getState() == 2) {
                        return;
                    }
                }
                String commandText = commandScanner.readCommand();
                Command command = commandParser.parseCommand(commandText);
                command.execute();
            } catch (CommandNotAvailableException | NoMoreFreeCoordinates | InvalidCommandArgumentsException |
                     NonExistentCommand commandException) {
                System.out.println(commandException.getMessage());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
