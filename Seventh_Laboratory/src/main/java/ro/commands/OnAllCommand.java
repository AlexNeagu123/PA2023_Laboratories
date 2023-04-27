package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.explorations.Exploration;
import ro.players.Robot;

/**
 * The <tt>OnCommand</tt> class is responsible for resuming all the {@link Robot} threads in the {@link Exploration}.
 * <p>
 * If the robots are currently running, nothing happens.
 */
@AllArgsConstructor
@Log4j2
public class OnAllCommand implements Command {
    private Exploration exploration;

    @Override
    public void execute() {
        System.out.println("The executions of all the robots are resumed.");
        for (Robot robot : exploration.getRobots()) {
            robot.setPaused(false);
        }
    }
}
