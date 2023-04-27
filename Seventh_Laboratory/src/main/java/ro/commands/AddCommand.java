package ro.commands;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.game.explorations.Exploration;
import ro.game.explorations.MatrixExploration;
import ro.players.Robot;
import ro.utils.NodeUtils;

/**
 * The <tt>AddCommand</tt> class is responsible for adding a {@link Robot} object in the {@link Exploration} process.
 * <p>
 * This command can be executed only before the exploration begins.
 */
@AllArgsConstructor
@Log4j2
public class AddCommand implements Command {
    Robot robot;

    @Override
    public void execute() {
        Exploration exploration = robot.getExploration();
        exploration.addRobot(robot);
        if (exploration instanceof MatrixExploration) {
            System.out.printf("Robot %s was created and placed on coordinates %s\n", robot.getName(),
                    NodeUtils.mapNodeToCoordinates(robot.getNodeId(), exploration.getMapLimit()));
        } else {
            System.out.printf("Robot %s was created and placed on node %d\n", robot.getName(), robot.getNodeId());
        }
    }
}