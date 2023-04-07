package com.graph;

import java.util.*;

/**
 * The <tt>Network</tt> class contains a list of {@link Node} objects along with the undirected relationships between them.
 * <p>
 * The importance of a <tt>Node</tt> in a network is the number of relationships it has with other nodes. In other words,
 * the network might be viewed as an undirected graph and the importance of a <tt>Node</tt> is the same as its degree.
 * <p>
 * For a better representation of the graph, each <tt>Node</tt> object inserted in the list is associated with a unique integer
 * called id. Thus, this class provides a simple and efficient way to work with the graph associated with the social network.
 * Several graph related algorithms might use a <tt>Network</tt> object as well.
 */
public class Network {
    private final List<Node> nodes = new ArrayList<>();
    private final Map<Node, Integer> nodeIds = new HashMap<>();
    private final List<List<Integer>> relationships = new ArrayList<>();

    /**
     * Adds a node (along with its relationships) to the network.
     * <p>
     * If a node with the same name has already been inserted to the network, nothing happens.
     * Otherwise, if the node has a unique name, an integer id is assigned to it.
     * <p>
     * The relationships of the newly added node are also added to the network.
     * A relationship is inserted into the network only if both nodes belong to the network.
     *
     * @param node The {@link Node} that is added to the network
     */

    public void addNode(Node node) {
        if (nodeIds.containsKey(node)) {
            return;
        }
        int nodeId = nodes.size();
        nodeIds.put(node, nodeId);
        relationships.add(new LinkedList<>());

        for (Node neighbour : node.getNeighbours()) {
            if (!nodeIds.containsKey(neighbour)) continue;
            int neighbourId = nodeIds.get(neighbour);
            relationships.get(nodeId).add(neighbourId);
            relationships.get(neighbourId).add(nodeId);
        }
        nodes.add(node);
    }

    /**
     * The content of the network is displayed on the screen in the following way:
     * <p>
     * All the {@link Node} objects from this network are printed on the screen (by name),
     * in increasing order of importance. The importance of a node is equal to the number of relationships it has
     * with other nodes (neighbours).
     * <p>
     * Additionally, the neighbours of each node are printed on the screen.
     */
    public void printNetwork() {
        List<Node> sortedNodes = new ArrayList<>(nodes);
        sortedNodes.sort(this::compareByImportance); // Sorts the nodes decreasingly by importance

        String delimiter = String.join("", Collections.nCopies(100, "-"));
        System.out.println("The network contains the following nodes (sorted by importance):");

        for (Node node : sortedNodes) {
            System.out.println(delimiter);
            System.out.printf("Node identified by \"%s\"\n", node.getName());
            System.out.println("Has importance " + getNodeImportance(node));
            System.out.print("Has relationships with the following nodes: ");
            int nodeId = nodeIds.get(node);
            for (Integer neighbourId : relationships.get(nodeId)) {
                System.out.printf("\"%s\" ", getNodeWithId(neighbourId).getName());
            }
            System.out.println();
        }
    }

    /**
     * @return The number of nodes from the network
     */
    public int getNodeCount() {
        return nodes.size();
    }

    /**
     * @param nodeId An integer between [0, nodeCount - 1] where nodeCount is the number of nodes in the network. This integer
     *               is the id of a uniquely determined {@link Node} object in the network.
     * @return A list containing the ids of all the neighbours of the <tt>Node</tt> associated with {@code nodeId}.
     */
    List<Integer> getNeighboursOfNodeId(int nodeId) {
        return new ArrayList<>(relationships.get(nodeId));
    }

    Node getNodeWithId(int nodeId) {
        return nodes.get(nodeId);
    }

    /**
     * For a given {@link Node}, this method computes the number of relationships it has (also defined as importance).
     *
     * @param node A <tt>Node</tt> object
     * @return The importance of the node
     */
    public int getNodeImportance(Node node) {
        int nodeId = nodeIds.get(node);
        return relationships.get(nodeId).size();
    }

    private int compareByImportance(Node a, Node b) {
        Integer aImportance = getNodeImportance(a);
        Integer bImportance = getNodeImportance(b);
        return bImportance.compareTo(aImportance);
    }
}
