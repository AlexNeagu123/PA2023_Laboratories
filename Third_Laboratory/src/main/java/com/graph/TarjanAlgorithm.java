package com.graph;

import java.util.*;

/**
 * The <tt>TarjanAlgorithm</tt> class contains an implementation of the Tarjan Algorithm designed for finding the
 * Biconnected Components in an undirected graph.
 * <p>
 * A <tt>TarjanAlgorithm</tt> object runs the Tarjan Algorithm on a given {@link Network} and computes both the <b>Articulation
 * Vertices</b> and the <b>Biconnected Components</b> in O(N + M) complexity where <tt>N</tt> is the number of
 * {@link Node} objects in the <tt>Network</tt> and <tt>M</tt> is the number of relationships that exist in the <tt>Network</tt>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Biconnected_component">Biconnected Components with Tarjan Algorithm</a>
 */
public class TarjanAlgorithm extends Algorithm {
    private final int[] lowestLevel;
    private final int[] timeIn;
    private int timer = 0;
    private final Deque<Integer> verticesStack = new ArrayDeque<>();
    private final List<Node> cutPoints = new ArrayList<>();
    private final List<List<Node>> biconnectedComponents = new ArrayList<>();

    /**
     * Constructs a new <tt>TarjanAlgorithm</tt> object that runs the algorithm on a <tt>Network</tt> instance.
     *
     * @param network The {@link Network} instance that will be processed by the algorithm
     */
    public TarjanAlgorithm(Network network) {
        super(network);
        this.lowestLevel = new int[nodeCount];
        this.timeIn = new int[nodeCount];
        initializeStructures();
        for (int nodeId = 0; nodeId < nodeCount; ++nodeId) {
            if (!visited[nodeId]) {
                dfsTraversal(nodeId, -1);
            }
        }
    }

    /**
     * @return A list containing all the <b>Articulation Vertices</b> in the <tt>Network</tt>
     */
    public List<Node> getCutPoints() {
        return new ArrayList<>(cutPoints);
    }

    /**
     * @return A list of lists representing the <b>Biconnected Components</b> of the <tt>Network</tt>
     */
    public List<List<Node>> getBiconnectedComponents() {
        List<List<Node>> copyBiconnectedComponents = new ArrayList<>();
        for (List<Node> sublist : biconnectedComponents) {
            copyBiconnectedComponents.add(new ArrayList<>(sublist));
        }
        return copyBiconnectedComponents;
    }

    /**
     * Initialization of the data structures used by the Tarjan Algorithm
     */
    private void initializeStructures() {
        Arrays.fill(lowestLevel, -1);
        Arrays.fill(timeIn, -1);
        Arrays.fill(visited, false);
    }

    /**
     * A dfs traversal of the {@link Network} that efficiently determines if the current {@link Node} is an
     * <b>articulation point</b>
     * <p>
     * At each step in the traversal, the current <tt>Node</tt> is being pushed to a stack.
     * <p>
     * If the current <tt>Node</tt> separates the subtree rooted in one of his <tt>sons</tt> from the rest of the
     * DFS Tree, nodes are being popped from the stack and added to a new biconnected component until the current node is reached.
     * Finally, the current node is also being added to the new biconnected component.
     *
     * @param nodeId The id of the current {@link Node} in the traversal
     * @param prevId The id of the previous <tt>Node</tt> in the traversal. If {@code nodeId} corresponds to the root,
     *               {@code prevId} has the value -1.
     */
    private void dfsTraversal(int nodeId, int prevId) {
        visited[nodeId] = true;
        timeIn[nodeId] = lowestLevel[nodeId] = timer++;
        verticesStack.addLast(nodeId);
        int childrenCount = 0;

        for (Integer neighbourId : network.getNeighboursOfNodeId(nodeId)) {
            if (neighbourId == prevId) {
                continue;
            }
            if (visited[neighbourId]) {
                lowestLevel[nodeId] = Math.min(lowestLevel[nodeId], timeIn[neighbourId]);
                continue;
            }
            dfsTraversal(neighbourId, nodeId);
            lowestLevel[nodeId] = Math.min(lowestLevel[nodeId], lowestLevel[neighbourId]);
            if (prevId != -1 && lowestLevel[neighbourId] >= timeIn[nodeId]) {
                cutPoints.add(network.getNodeWithId(nodeId));
                addNewComponent(nodeId, false);
            }
            childrenCount++;
        }
        if (prevId == -1) {
            // the node is a root, so all the remaining nodes in the stack are popped and form the last biconnected component
            addNewComponent(nodeId, true);
            if (childrenCount > 1) {
                // mark the root as articulation point only if it has more than 1 child in the DFS Tree
                cutPoints.add(network.getNodeWithId(nodeId));
            }
        }
    }

    /**
     * Pops nodes from the stack and added them to a newly created biconnected component until a specified <tt>Node</tt>
     * is met.
     *
     * @param nodeId The id of the final <tt>Node</tt> added to the newly created biconnected component. If this node is the root
     *               of the tree, it also gets popped from the stack, otherwise not
     * @param isRoot Is <tt>true</tt> if the node specified by {@code nodeId} is the root of the DFS Tree and <tt>false</tt>
     *               otherwise
     */
    private void addNewComponent(int nodeId, boolean isRoot) {
        biconnectedComponents.add(new ArrayList<>());
        int componentIndex = biconnectedComponents.size() - 1;
        int topNodeId = -1;

        do {
            topNodeId = verticesStack.getLast();
            biconnectedComponents.get(componentIndex).add(network.getNodeWithId(topNodeId));
            // If nodeId is reached, it is added to the component, but is not popped from the stack
            if (topNodeId != nodeId) verticesStack.removeLast();
        } while (topNodeId != nodeId);

        if (isRoot) {
            // If nodeId is the root, pop it from the stack to clean it entirely
            verticesStack.removeLast();
        }
    }
}
