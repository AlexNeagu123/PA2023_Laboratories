package com.problem;

import com.locations.Location;
import com.roads.Road;
import lombok.Getter;
import lombok.ToString;

/**
 * The {@code Problem} class stores an array of {@link Location} objects and an array of {@link Road} objects.
 * It represents an instance of a shortest-path problem, where finding a path with minimum total length
 * between two {@code Location} objects is required.
 */
@ToString
public class Problem {
    @Getter
    private int locationCount = 0;
    @Getter
    private int roadCount = 0;
    private Location[] locations;
    private Road[] roads;

    /**
     * Creates a {@code Problem} object without any locations or roads.
     */
    public Problem() {
        locations = new Location[1];
        roads = new Road[1];
    }

    /**
     * Creates a {@code Problem} object with a set of locations and no roads.
     *
     * @param locations An array of {@code Location} objects
     */
    public Problem(Location[] locations) {
        this();
        this.locations = locations;
        this.locationCount = locations.length;
    }

    /**
     * Creates a {@code Problem} object with a set of locations and roads.
     *
     * @param locations An array of {@code Location} objects
     * @param roads     An array of {@code Road} objects
     */
    public Problem(Location[] locations, Road[] roads) {
        this(locations);
        this.roads = roads;
        this.roadCount = roads.length;
    }

    /**
     * Return the position (0-indexed), from the array of locations, of a specific location
     *
     * @param location A {@link Location} whose position is searched
     * @return The position (0-indexed) of the searched location
     */
    public int getLocationIndex(Location location) {
        for (int i = 0; i < locationCount; ++i) {
            if (locations[i].equals(location)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the {@link Location} on a specific position in the array of locations
     *
     * @param locationIndex The position (0-indexed) of a {@code Location} object
     * @return The location on the desired position
     */
    public Location getLocationOnIndex(int locationIndex) {
        return locations[locationIndex];
    }

    /**
     * Returns the {@link Road} with the minimum length between to given {@link Location} objects representing road endpoints
     *
     * @param firstEndpoint  The first endpoint of the road
     * @param secondEndpoint The second endpoint of the road
     * @return The road with minimum length between the endpoints
     */
    public Road findShortestRoadByEndpoints(Location firstEndpoint, Location secondEndpoint) {
        double shortestLength = Double.MAX_VALUE;
        int bestRoadIndex = 0;
        for (int i = 0; i < roadCount; ++i) {
            if (firstEndpoint.equals(roads[i].getFirstEndpoint()) && secondEndpoint.equals(roads[i].getSecondEndpoint())
                    || (firstEndpoint.equals(roads[i].getSecondEndpoint()) && secondEndpoint.equals(roads[i].getFirstEndpoint()))
                    && roads[i].getLength() < shortestLength) {
                shortestLength = roads[i].getLength();
                bestRoadIndex = i;
            }
        }
        return roads[bestRoadIndex];
    }

    public Location[] getLocations() {
        Location[] returnedLocations = new Location[locationCount];
        System.arraycopy(locations, 0, returnedLocations, 0, locationCount);
        return returnedLocations;
    }

    public Road[] getRoads() {
        Road[] returnedRoads = new Road[roadCount];
        System.arraycopy(roads, 0, returnedRoads, 0, roadCount);
        return returnedRoads;
    }

    private void locationsRealloc() {
        Location[] newLocationList = new Location[locationCount * 2];
        if (locationCount >= 0) {
            System.arraycopy(locations, 0, newLocationList, 0, locationCount);
        }
        locations = newLocationList;
    }

    private void roadsRealloc() {
        Road[] newRoadList = new Road[roadCount * 2];
        if (roadCount >= 0) {
            System.arraycopy(roads, 0, newRoadList, 0, roadCount);
        }
        roads = newRoadList;
    }

    /**
     * Adds a {@link Location} object in the array of locations
     *
     * @param newLocation The location inserted
     */
    public void addLocation(Location newLocation) {
        for (int i = 0; i < locationCount; ++i) {
            if (newLocation.equals(locations[i])) {
                return;
            }
        }
        if (locationCount == locations.length) {
            locationsRealloc();
        }
        locations[locationCount++] = newLocation;
    }

    /**
     * Adds a {@link Road} object in the array of roads
     *
     * @param newRoad The road inserted
     */
    public void addRoad(Road newRoad) {
        for (int i = 0; i < roadCount; ++i) {
            if (newRoad.equals(roads[i])) {
                return;
            }
        }
        if (roadCount == roads.length) {
            roadsRealloc();
        }
        roads[roadCount++] = newRoad;
    }
}
