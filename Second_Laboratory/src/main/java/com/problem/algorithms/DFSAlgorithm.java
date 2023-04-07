package com.problem.algorithms;

import com.exceptions.InvalidQuery;
import com.locations.Location;
import com.problem.Problem;
import com.roads.Road;
import com.exceptions.InvalidProblem;

import java.util.Arrays;

/**
 * The {@code DFSAlgorithm} class is able to check whether a path between two {@link Location} objects exists.
 * <p>
 * In order to check the existence of a path, a DFS traversal is used.
 * <p>
 * A {@code Location} object is transformed into an integer (each location is changed with its
 * corresponding index in the locations array). In addition, each {@link Road} object is viewed as an edge between
 * two integer nodes. Thus, an adjacency matrix is created and used further in DFS traversals. The time complexity of doing
 * these transformations is O(locationCount * roadCount).
 */
public class DFSAlgorithm extends Algorithm {
    private final boolean[] visited;
    private final int[][] adjMatrix;

    /**
     * Constructs the adjacency matrix of the undirected graph characterized by the {@code Locations} and {@code Roads}.
     *
     * @param problemInstance The {@link Problem} that stores the roads and locations used by this algorithm
     * @throws InvalidProblem If the roads given in the {@code Problem} contains endpoints that are not stored in the locations array
     */
    public DFSAlgorithm(Problem problemInstance) throws InvalidProblem {
        super(problemInstance);

        // Allocate memory
        this.adjMatrix = new int[locationCount][locationCount];
        this.visited = new boolean[locationCount];

        // Compute the adjacency matrix
        for (Road road : problemInstance.getRoads()) {
            int firstEndpointIndex = problemInstance.getLocationIndex(road.getFirstEndpoint());
            int secondEndpointIndex = problemInstance.getLocationIndex(road.getSecondEndpoint());
            adjMatrix[firstEndpointIndex][secondEndpointIndex] = 1;
            adjMatrix[secondEndpointIndex][firstEndpointIndex] = 1;
        }
    }

    /**
     * Checks if a path exists between two given {@link Location} objects.
     * A DFS traversal is used.
     * Because the graph is stored as an adjacency matrix, the total complexity of the traversal is O(locationCount ^ 2).
     *
     * @param startLocation The starting {@code Location}
     * @param endLocation   The ending {@code Location}
     * @return {@code true} if a path that starts in {@code startLocation} and ends in {@code endLocation} exists, {@code false}
     * otherwise
     * @throws InvalidQuery If one of the locations (or both) are <b>not found</b> in the problem instance
     */
    public boolean pathExists(Location startLocation, Location endLocation) throws InvalidQuery {
        int startLocationIndex = problemInstance.getLocationIndex(startLocation);
        int endLocationIndex = problemInstance.getLocationIndex(endLocation);

        if (startLocationIndex == -1 || endLocationIndex == -1) {
            throw new InvalidQuery();
        }

        Arrays.fill(visited, false);
        dfsTraversal(startLocationIndex);

        return visited[endLocationIndex];
    }

    /**
     * A recursive dfs traversal used by the {@code pathExists} method.
     * <p>
     * Because the graph is stored as an adjacency matrix, the total complexity of the traversal is O(locationCount ^ 2)
     *
     * @param locationIndex The current node in the traversal
     */
    private void dfsTraversal(int locationIndex) {
        // mark current node as visited
        visited[locationIndex] = true;
        // Propagate recursion through unvisited neighbours
        for (int neighbourIndex = 0; neighbourIndex < locationCount; ++neighbourIndex) {
            if (adjMatrix[locationIndex][neighbourIndex] == 1 && !visited[neighbourIndex]) {
                dfsTraversal(neighbourIndex);
            }
        }
    }
}