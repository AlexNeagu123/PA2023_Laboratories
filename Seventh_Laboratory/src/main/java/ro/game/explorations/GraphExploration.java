package ro.game.explorations;

import lombok.extern.log4j.Log4j2;
import ro.app.TimeKeeper;
import ro.game.maps.GraphMap;
import ro.shared.SharedMemory;

/**
 * The <tt>GraphExploration</tt> class represents a graph exploration.
 * <p>
 * Several arguments are initialized differently compared to the {@link MatrixExploration} such as the number of tokens
 * in the {@link SharedMemory} and the {@link ro.game.maps.ExplorationMap}
 */
@Log4j2
public class GraphExploration extends Exploration {
    private final int nodeCount;

    public GraphExploration(TimeKeeper timeKeeper, int nodeCount, int edgeCount) {
        super(timeKeeper);
        this.nodeCount = nodeCount;
        this.visited = new boolean[nodeCount];
        this.sharedMemory = new SharedMemory((int) Math.pow(nodeCount, 2));
        this.explorationMap = new GraphMap(nodeCount, edgeCount);
        initializeFreeNodes();
    }

    private void initializeFreeNodes() {
        for (int nodeId = 0; nodeId < nodeCount; ++nodeId) {
            freeStartNodes.add(nodeId);
        }
    }

    @Override
    public int getMapLimit() {
        return nodeCount;
    }
}
