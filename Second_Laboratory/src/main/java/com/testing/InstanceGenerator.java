package com.testing;

import com.exceptions.InvalidEndpoints;
import com.exceptions.InvalidLength;
import com.locations.City;
import com.locations.Location;
import com.problem.Problem;
import com.roads.HighwayRoad;

/**
 * This class is able to generate a random instance of the shortest path problem.
 * <p>
 * All the locations generated are {@link City} objects and all the roads are {@link HighwayRoad} objects.
 * <p>
 * The names of the cities correspond to their position in the array
 * <p>
 * Up to 10000 {@code Locations} might be generated. All of them are situated on coordinates between 0 and 1 (both dimensions)
 * <p>
 * Up to 10000 {@code Roads} might be generated. All of them have length at least 2.
 */
public class InstanceGenerator {
    /**
     * Generates a random instance of the shortest path problem
     *
     * @return A {@link Problem} object that describes the problem instance that has been generated
     * @throws InvalidLength    If the {@code length} of some road is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@code Location} endpoints
     * @throws InvalidEndpoints If the endpoints of some road are <b>the same</b>
     */
    public static Problem generateProblemInstance() throws InvalidLength, InvalidEndpoints {
        // Randomly generate location and road counts (between 5 and 1000)
        int locationCount = (int) (5 + Math.random() * 995);
        int roadCount = (int) (5 + Math.random() * 995);

        Problem problemInstance = new Problem();

        for (int i = 0; i < locationCount; ++i) {
            // Add locations situated at random cartesian coordinates between [0..1]
            problemInstance.addLocation(new City(Integer.toString(i), Math.random(), Math.random(), 10000));
        }

        for (int i = 0; i < roadCount; ++i) {
            int firstEndpointIndex = (int) (Math.random() * (locationCount - 1));
            int secondEndpointIndex = firstEndpointIndex;
            while (secondEndpointIndex == firstEndpointIndex) {
                secondEndpointIndex = (int) (Math.random() * (locationCount - 1));
            }

            Location firstEndpoint = problemInstance.getLocationOnIndex(firstEndpointIndex);
            Location secondEndpoint = problemInstance.getLocationOnIndex(secondEndpointIndex);

            // Randomly generate the road length (between 2 and 10000)
            int length = (int) (2 + Math.random() * 9998);
            problemInstance.addRoad(new HighwayRoad(length, firstEndpoint, secondEndpoint));
        }

        return problemInstance;
    }
}
