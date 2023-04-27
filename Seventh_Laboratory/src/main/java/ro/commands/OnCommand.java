package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.explorations.Exploration;
import ro.players.Robot;

/**
 * The <tt>OnCommand</tt> class is responsible for resuming a specific {@link Robot} thread in the {@link Exploration}.
 * <p>
 * The {@code name} of the robot should be provided when the constructor is called.
 * <p>
 * If the specified robot is currently running, nothing happens.
 */
@AllArgsConstructor
@Log4j2
public class OnCommand implements Command {
    String robotName;
    Exploration exploration;

    @Override
    public void execute() {
        for (Robot robot : exploration.getRobots()) {
            if (robot.getName().equals(robotName)) {
                System.out.printf("The execution of %s is resumed.\n", robot);
                robot.setPaused(false);
                break;
            }
        }
    }
}
