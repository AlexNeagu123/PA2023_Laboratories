package ro.players;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.game.explorations.Exploration;
import ro.exceptions.NoMoreFreeCoordinates;
import ro.shared.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * The <tt>Robot</tt> class implements all the logic for visiting nodes from an {@link ro.game.maps.ExplorationMap} in a systematic way.
 * <p>
 * If multiple <tt>Robot</tt> threads are created and started, the robots are synchronized with each other.
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class Robot implements Runnable {
    private final Exploration exploration;
    @ToString.Include
    private final String name;
    private Integer nodeId;
    @Setter
    private boolean running;
    @Setter
    private boolean paused;
    private int tokensPlaced;
    private final Deque<Integer> dfsStack;

    /**
     * A new robot is created and placed on a <b>randomly</b> chosen node from the {@link ro.game.maps.ExplorationMap}
     *
     * @param name        The name of the newly created robot
     * @param exploration The {@link Exploration} in which this <tt>Robot</tt> participates
     * @throws NoMoreFreeCoordinates If there are no more free nodes in the map
     */
    public Robot(String name, Exploration exploration) throws NoMoreFreeCoordinates {
        this.name = name;
        this.tokensPlaced = 0;
        this.exploration = exploration;
        this.nodeId = exploration.generateValidNode();
        this.dfsStack = new ArrayDeque<>();
        this.running = true;
        this.paused = false;
    }

    /**
     * @return A list of {@link Token} objects extracted from the {@link ro.shared.SharedMemory}
     */
    public List<Token> extractTokensFromSharedData() {
        List<Token> extractedTokens = exploration.getSharedMemory().extractTokens(exploration.getMapLimit());
        tokensPlaced += extractedTokens.size();
        return extractedTokens;
    }

    private void updateDfsStack(List<Integer> neighbours) {
        for (Integer neighbourId : neighbours) {
            if (exploration.setVisitedIfNecessary(neighbourId)) {
                dfsStack.add(neighbourId);
            }
        }
    }

    /**
     * Executes an <b>iterative dfs</b> on the {@link ro.game.maps.ExplorationMap}.
     * <p>
     * At each step, the robot places {@link Token} objects in the {@link ro.game.maps.Cell} corresponding to the current node.
     * <p>
     * All the unvisited neighbours are pushed to a stack and marked as visited.
     */
    @Override
    public void run() {
        exploration.setVisitedIfNecessary(nodeId);
        dfsStack.clear();
        dfsStack.add(nodeId);
        while (running && !dfsStack.isEmpty()) {
            if (paused) {
                continue;
            }
            try {
                nodeId = dfsStack.pollLast();
                exploration.visit(nodeId, this);
                updateDfsStack(exploration.getNeighbours(nodeId));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (this.running) {
            this.running = false;
        }
    }
}
