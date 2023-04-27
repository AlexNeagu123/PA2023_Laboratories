package ro.commands;

import ro.game.explorations.Exploration;
import ro.players.Robot;

/**
 * The <tt>QuitCommand</tt> class is responsible for exiting the application.
 */
public class QuitCommand implements Command {
    @Override
    public void execute() {
        System.exit(0);
    }
}
