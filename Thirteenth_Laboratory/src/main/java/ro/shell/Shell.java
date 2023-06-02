package ro.shell;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ro.command.Command;
import ro.exception.InvalidCommandArguments;
import ro.exception.NonExistentCommand;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The <tt>Shell</tt> class interacts with the user through the command line
 */
@AllArgsConstructor
@Log4j2
public class Shell {
    private final CommandScanner commandScanner;
    private final CommandParser commandParser;
    @Setter
    @Getter
    private Locale locale;
    @Setter
    @Getter
    private ResourceBundle resourceBundle;

    public void execute() {
        while (true) {
            try {
                System.out.println(resourceBundle.getString("prompt"));
                String commandName = commandScanner.readCommand();
                Command command = commandParser.parseCommand(commandName);
                command.execute(this);
            } catch (NonExistentCommand ex) {
                System.out.println(resourceBundle.getString("invalid"));
            } catch (InvalidCommandArguments ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
