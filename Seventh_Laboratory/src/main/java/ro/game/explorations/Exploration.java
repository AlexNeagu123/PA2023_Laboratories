package ro.game.explorations;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ro.app.TimeKeeper;
import ro.exceptions.NoMoreFreeCoordinates;
import ro.game.maps.ExplorationMap;
import ro.players.Robot;
import ro.shared.SharedMemory;
import ro.shared.Token;

import java.util.*;

/**
 * The <tt>Exploration</tt> class provides a skeletal implementation for different types of explorations (matrix or graph).
 * <p>
 * An exploration consists of one or more {@link Robot} threads that concurrently visit nodes from the graph/matrix,
 * and place a fixed number of {@link Token} objects extracted from the {@link SharedMemory} in that node.
 * <p>
 * In both types of explorations, the nodes are represented as {@link Integer} ids.
 * <p>
 * In case of a matrix exploration,
 * the {@link ro.game.maps.Coordinates} of a cell are mapped to an integer.
 */
@Getter
@Log4j2
public abstract class Exploration implements Runnable {
    protected final List<Robot> robots;
    protected final Set<Integer> freeStartNodes;
    protected boolean[] visited;
    protected final TimeKeeper timeKeeper;
    protected SharedMemory sharedMemory;
    protected ExplorationMap explorationMap;
    @Setter
    protected int state;

    /**
     * @param timeKeeper The {@link TimeKeeper} daemon that keeps evidence of the running time
     */
    public Exploration(TimeKeeper timeKeeper) {
        this.robots = new ArrayList<>();
        this.freeStartNodes = new HashSet<>();
        this.timeKeeper = timeKeeper;
        this.timeKeeper.setDaemon(true);
        this.state = 0;
    }

    /**
     * @return The number of nodes in case of a graph exploration, and the matrix length in case of a matrix exploration
     */
    public abstract int getMapLimit();

    /**
     * @param nodeId The id of a node
     * @return A list of neighbours for the given node
     */
    public List<Integer> getNeighbours(int nodeId) {
        return explorationMap.getNeighbours(nodeId);
    }

    /**
     * @param robotName The name of a robot
     * @return <tt>true</tt> if there is no robot with {@code robotName} in the exploration, <tt>false</tt> otherwise
     */
    public boolean hasUniqueName(String robotName) {
        for (Robot robot : robots) {
            if (robot.getName().equals(robotName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a {@link Robot} object in this exploration
     *
     * @param robot The robot to be added
     */
    public void addRobot(Robot robot) {
        robots.add(robot);
    }

    public void visit(Integer nodeId, Robot robot) {
        explorationMap.visit(nodeId, robot);
    }

    /**
     * Marks a node as visited if it isn't already visited
     * <p>
     * This is an important, atomic operation in the exploration
     *
     * @param nodeId The node to be verified
     * @return <tt>true</tt> if the node is not visited and is successfully set, <tt>false</tt> otherwise
     */
    public synchronized boolean setVisitedIfNecessary(Integer nodeId) {
        if (visited[nodeId]) {
            return false;
        }
        visited[nodeId] = true;
        return true;
    }

    /**
     * @return A node that doesn't have a robot placed on it
     * @throws NoMoreFreeCoordinates If all the nodes have robots on them
     */
    public Integer generateValidNode() throws NoMoreFreeCoordinates {
        if (freeStartNodes.isEmpty()) {
            throw new NoMoreFreeCoordinates();
        }
        Integer nodeId = freeStartNodes.stream().skip(new Random().nextInt(freeStartNodes.size())).findFirst().orElse(null);
        assert nodeId != null;
        freeStartNodes.remove(nodeId);
        return nodeId;
    }

    /**
     * Checks if the time limit imposed by the {@link TimeKeeper} daemon has passed.
     * <p>
     * In affirmative case, all the {@link Robot} threads are stopped from running
     *
     * @return <tt>true</tt> if the time limit has been exceeded, <tt>false</tt> otherwise
     */
    protected boolean timeLimitIsExceeded() {
        if (!timeKeeper.isAlive()) {
            for (Robot robot : robots) {
                robot.setRunning(false);
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if all the {@link Robot} threads have stopped have finished the exploration.
     *
     * @return <tt>true</tt> in affirmative case, <tt>false</tt> otherwise
     */
    protected boolean allRobotsAreFinished() {
        boolean noRobotsLeft = true;
        for (Robot robot : robots) {
            if (robot.isRunning()) {
                noRobotsLeft = false;
                break;
            }
        }
        return noRobotsLeft;
    }

    /**
     * Starts the execution of the {@link Robot} threads.
     * <p>
     * When the exploration finishes, the final state of the board is displayed on the screen
     */
    @Override
    public void run() {
        timeKeeper.start();
        List<Thread> threads = new ArrayList<>();
        this.state = 1;

        for (Robot robot : robots) {
            threads.add(new Thread(robot));
        }
        for (Thread thread : threads) {
            thread.start();
        }

        while (!timeLimitIsExceeded() && !allRobotsAreFinished()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }

        System.out.println("The final state of the board looks like this:");
        explorationMap.printFinalState();
        System.out.println("The number of tokens placed in the board by each robot:");
        for (Robot robot : robots) {
            System.out.printf("Robot %s have placed %d tokens in the board\n", robot.getName(), robot.getTokensPlaced());
        }
        this.state = 2;
    }

    public static void printTokenList(List<Token> tokenList) {
        System.out.print("[");
        for (int k = 0; k < tokenList.size(); ++k) {
            if (k + 1 < tokenList.size()) {
                System.out.print(tokenList.get(k).getNumber() + ", ");
            } else {
                System.out.print(tokenList.get(k).getNumber());
            }
        }
        System.out.println("]");
    }
}
