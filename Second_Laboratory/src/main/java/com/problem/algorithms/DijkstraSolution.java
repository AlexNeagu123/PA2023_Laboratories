package com.problem.algorithms;

import com.locations.Location;
import com.problem.Problem;
import com.roads.Road;

/**
 * The {@code DijkstraSolution} class stores the locations and roads contained in the shortest path found by a
 * {@link DijkstraAlgorithm} object.
 */
public class DijkstraSolution {
    public final Location[] route;
    public final Road[] roadsTravelled;

    /**
     * Initializes a solution with no routes and roads travelled
     */
    DijkstraSolution() {
        this.route = new Location[0];
        this.roadsTravelled = new Road[0];
    }

    /**
     * Initializes a solution with the route given as parameter.
     * <p>
     * The roads travelled are also deduced from the route
     *
     * @param route           An array of {@link Location} objects that are contained in the shortest path found
     *                        by the {@link DijkstraAlgorithm}
     * @param problemInstance The problem instance solved by the {@code DijkstraAlgorithm}
     */
    DijkstraSolution(Location[] route, Problem problemInstance) {
        this.route = route;
        this.roadsTravelled = new Road[route.length - 1];
        for (int i = 0; i < route.length - 1; ++i) {
            this.roadsTravelled[i] = problemInstance.findShortestRoadByEndpoints(route[i], route[i + 1]);
        }
    }

    /**
     * @return The total length of the shortest path found by the {@link DijkstraAlgorithm}
     */
    public double computeLength() {
        double totalLength = 0;
        for (Road road : roadsTravelled) {
            totalLength += road.getLength();
        }
        return roadsTravelled.length == 0 ? -1 : totalLength;
    }

}
