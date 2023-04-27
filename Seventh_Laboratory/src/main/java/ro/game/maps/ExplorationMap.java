package ro.game.maps;

import ro.players.Robot;

import java.util.List;

/**
 * The <tt>ExplorationMap</tt> interface expose the base methods that can be executed on the matrix/graph map
 */
public interface ExplorationMap {
    /**
     * A node is visited by a specified {@link Robot} thread.
     * <p>
     * Several {@link ro.shared.Token} objects are extracted from the {@link ro.shared.SharedMemory} by the robot and placed
     * inside the {@link Cell}
     *
     * @param nodeId The id of the visited node
     * @param robot  The {@link Robot} thread that is visiting the node
     */
    void visit(Integer nodeId, Robot robot);

    /**
     * Prints the final state of the map
     */
    void printFinalState();

    /**
     * Gets all the neighbours of a given node in the map
     *
     * @param nodeId The id of the node
     * @return The list of neighbours of the node
     */
    List<Integer> getNeighbours(int nodeId);
}
