package ro.shell;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.command.Command;
import ro.exception.*;

/**
 * The <tt>Shell</tt> class interacts with the user through the command line
 */
@AllArgsConstructor
@Log4j2
public class Shell {
    private final CommandScanner commandScanner;
    private final CommandParser commandParser;

    public void execute() {
        while (true) {
            try {
                String commandName = commandScanner.readCommand();
                Command command = commandParser.parseCommand(commandName);
                command.execute();
            } catch (InvalidClass | NonExistentCommand | InvalidSystemPath | InvalidCommandArguments |
                     InvalidDirectory | InvalidJar ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
