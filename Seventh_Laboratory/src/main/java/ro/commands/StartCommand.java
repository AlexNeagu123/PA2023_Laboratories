package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.explorations.Exploration;

/**
 * The <tt>StartCommand</tt> class is responsible for starting the {@link Exploration} process.
 * <p>
 * This command can be called only once. After this command is called, no more {@link AddCommand} commands can be called.
 */
@AllArgsConstructor
@Log4j2
public class StartCommand implements Command {
    Exploration exploration;

    @Override
    public void execute() {
        System.out.println("The exploration process have been started...");
        new Thread(exploration).start();
    }
}
