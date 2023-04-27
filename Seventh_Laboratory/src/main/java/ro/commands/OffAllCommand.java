package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.explorations.Exploration;
import ro.players.Robot;

/**
 * The <tt>OffAllCommand</tt> class is responsible for pausing all the {@link Robot} threads in the {@link Exploration}.
 * <p>
 * If the robots are already paused, nothing happens.
 */
@AllArgsConstructor
@Log4j2
public class OffAllCommand implements Command {
    private Exploration exploration;

    @Override
    public void execute() {
        System.out.println("The executions of all the robots are paused.");
        for (Robot robot : exploration.getRobots()) {
            robot.setPaused(true);
        }
    }
}
