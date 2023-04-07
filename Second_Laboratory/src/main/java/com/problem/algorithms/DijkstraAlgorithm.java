package com.problem.algorithms;

import com.exceptions.InvalidProblem;
import com.exceptions.InvalidQuery;
import com.locations.Location;
import com.problem.Problem;
import com.roads.Road;

import java.util.Arrays;

/**
 * The {@code DijkstraAlgorithm} class is able to find the shortest path between two {@link Location} objects.
 * <p>
 * A {@code Location} object is transformed into an integer (each location is changed with its
 * corresponding index in the locations array). In addition, each {@link Road} object is viewed as an edge between
 * two integer nodes that also has a given length. Thus, an adjacency matrix with costs is created.
 * Cell (i, j) in the matrix will store the length of the road from the i-th location to the j-th location.
 * The time complexity of doing these transformations is O(locationCount * roadCount).
 */
public class DijkstraAlgorithm extends Algorithm {
    private final double[][] adjMatrix;
    private final boolean[] inQueue;
    private final double[] shortestPath;
    private final int[] comesFrom;

    /**
     * Constructs the adjacency matrix of the undirected weighted graph characterized by {@code Locations} and {@code Roads}.
     *
     * @param problemInstance The {@link Problem} that stores the roads and locations used by this algorithm
     * @throws InvalidProblem If the roads given in the {@code Problem} contains endpoints that are not stored in the locations array
     */
    public DijkstraAlgorithm(Problem problemInstance) throws InvalidProblem {
        super(problemInstance);

        // Allocating Memory
        this.adjMatrix = new double[locationCount][locationCount];
        this.inQueue = new boolean[locationCount];
        this.shortestPath = new double[locationCount];
        this.comesFrom = new int[locationCount];

        // Initializing the Adjacency Matrix with very big values
        for (int i = 0; i < locationCount; ++i) {
            for (int j = 0; j < locationCount; ++j) {
                adjMatrix[i][j] = Double.MAX_VALUE;
            }
        }

        /*
            Computing the Adjacency Matrix.
            adjMatrix[i][j] = Double.MAX_VALUE iff a road doesn't exist between these locations
        */

        for (Road road : problemInstance.getRoads()) {
            int firstEndpointIndex = problemInstance.getLocationIndex(road.getFirstEndpoint());
            int secondEndpointIndex = problemInstance.getLocationIndex(road.getSecondEndpoint());
            adjMatrix[firstEndpointIndex][secondEndpointIndex] = Math.min(road.getLength(), adjMatrix[firstEndpointIndex][secondEndpointIndex]);
            adjMatrix[secondEndpointIndex][firstEndpointIndex] = Math.min(road.getLength(), adjMatrix[secondEndpointIndex][firstEndpointIndex]);
        }
    }

    /**
     * Computes the shortest path between two {@link Location} objects
     * Because the graph is stored as an adjacency matrix, the total complexity of the algorithm is O(locationCount ^ 2).
     *
     * @param startLocation The starting {@code Location}
     * @param endLocation   The ending {@code Location}
     * @return A {@link DijkstraSolution} object that stores the entire shortest path that begins in {@code startLocation}
     * and ends in {@code endLocation}
     * @throws InvalidQuery If one of the locations (or both) are <b>not found</b> in the problem instance
     */
    public DijkstraSolution computeShortestPath(Location startLocation, Location endLocation) throws InvalidQuery {
        int startLocationIndex = problemInstance.getLocationIndex(startLocation);
        int endLocationIndex = problemInstance.getLocationIndex(endLocation);

        if (startLocationIndex == -1 || endLocationIndex == -1) {
            throw new InvalidQuery();
        }

        Arrays.fill(inQueue, false);
        Arrays.fill(shortestPath, Double.MAX_VALUE);
        Arrays.fill(comesFrom, -1);

        inQueue[startLocationIndex] = true;
        shortestPath[startLocationIndex] = 0;

        while (true) {
            int closestLocationIndex = -1;
            // From all locations in the queue, select the one with minimum shortest distance
            for (int i = 0; i < locationCount; ++i) {
                if (inQueue[i] && (closestLocationIndex == -1 || shortestPath[closestLocationIndex] > shortestPath[i])) {
                    closestLocationIndex = i;
                }
            }

            // There is no Location left in the Queue
            if (closestLocationIndex == -1) {
                break;
            }
            // Pop from queue
            inQueue[closestLocationIndex] = false;

            // Visit neighbours
            for (int i = 0; i < locationCount; ++i) {
                if (adjMatrix[closestLocationIndex][i] == Double.MAX_VALUE) {
                    continue;
                }
                // If a better path is found
                if (shortestPath[closestLocationIndex] + adjMatrix[closestLocationIndex][i] < shortestPath[i]) {
                    shortestPath[i] = shortestPath[closestLocationIndex] + adjMatrix[closestLocationIndex][i];
                    comesFrom[i] = closestLocationIndex;
                    inQueue[i] = true;
                }
            }
        }

        // There is no path from the starting Location to the ending Location
        if (shortestPath[endLocationIndex] == Double.MAX_VALUE) {
            return new DijkstraSolution();
        }

        // Shortest path exists
        Location[] reversedRoute = new Location[locationCount];
        int routeLength = 0;

        // Using the comesFrom array to reconstitute the reversed solution
        while (endLocationIndex != -1) {
            reversedRoute[routeLength++] = problemInstance.getLocationOnIndex(endLocationIndex);
            endLocationIndex = comesFrom[endLocationIndex];
        }
        // Build the final route
        Location[] route = new Location[routeLength];
        for (int i = routeLength - 1; i >= 0; --i) {
            route[routeLength - 1 - i] = reversedRoute[i];
        }
        return new DijkstraSolution(route, problemInstance);
    }

}
