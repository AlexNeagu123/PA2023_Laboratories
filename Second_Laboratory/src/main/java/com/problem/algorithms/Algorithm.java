package com.problem.algorithms;

import com.exceptions.InvalidProblem;
import com.locations.Location;
import com.problem.Problem;
import com.roads.Road;

/**
 * The {@code Algorithm} class provides a skeletal implementation of different algorithms that might be implemented to solve
 * the {@code Problem}. {@link DFSAlgorithm} and {@link DijkstraAlgorithm} are inherited from this class.
 * <p>
 * The problem instance and the number of locations are stored directly in this class.
 */
public abstract class Algorithm {
    protected Problem problemInstance;
    protected int locationCount;

    /**
     * Initializes the {@link Problem} object and the number of locations.
     *
     * @param problemInstance The problem instance where these algorithms are used from
     * @throws InvalidProblem If the roads given in the {@code Problem} contains endpoints that are not stored in the locations array
     */
    public Algorithm(Problem problemInstance) throws InvalidProblem {
        if (!Algorithm.isValidProblemInstance(problemInstance)) {
            throw new InvalidProblem();
        }
        this.problemInstance = problemInstance;
        this.locationCount = problemInstance.getLocationCount();
    }

    /**
     * Checks if a problem instance is valid. A problem instance is valid if all the roads have
     * the endpoints stored in the array of locations.
     * @param problemInstance The problem instance that is checked
     * @return {@code true} if the problem instance is valid, {@code false} otherwise
     */
    public static boolean isValidProblemInstance(Problem problemInstance) {
        Road[] roads = problemInstance.getRoads();
        Location[] locations = problemInstance.getLocations();

        // Check if all the roads has valid endpoints
        for (Road road : roads) {
            boolean firstFound = false, secondFound = false;
            for (Location location : locations) {
                if (location.equals(road.getFirstEndpoint()))
                    firstFound = true;
                if (location.equals(road.getSecondEndpoint()))
                    secondFound = true;
            }
            if (!firstFound || !secondFound) {
                return false;
            }
        }
        return true;
    }

}
