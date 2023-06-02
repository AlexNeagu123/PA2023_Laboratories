package ro.shell;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Scanner;

/**
 * The <tt>CommandScanner</tt> class is responsible for reading commands typed in the terminal by the user.
 */
@AllArgsConstructor
@Log4j2
public class CommandScanner {
    private Scanner scanner;

    /**
     * @return A line of text is read from the terminal
     */
    public String readCommand() {
        String commandText = "";
        while (commandText.length() == 0) {
            commandText = scanner.nextLine();
        }
        return commandText;
    }
}
