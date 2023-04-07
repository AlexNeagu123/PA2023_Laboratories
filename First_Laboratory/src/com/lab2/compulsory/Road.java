package com.lab2.compulsory;

import com.lab2.compulsory.enums.RoadType;
import com.lab2.compulsory.exceptions.InvalidLength;
import com.lab2.compulsory.math.Utils;

/**
 * The {@code Road} class represents a road that connects two distinct {@code Locations}.
 * Additionally, this class stores the type of the road (HIGHWAY, EXPRESS or COUNTRY), the length of the road
 * and the speed limit on the road.
 */
public class Road {
    private RoadType type;
    private double length;
    private int speedLimit = 80;
    private Location firstEndpoint;
    private Location secondEndpoint;

    /**
     * Constructs a road between two {@link Location} objects, with speed limit set to 80 km/h.
     * @param type The type of the newly created road
     * @param length The length of the newly created road
     * @param firstEndpoint One of the endpoints of the road
     * @param secondEndpoint The other endpoint of the road
     * @throws InvalidLength If the {@code length} of the road is <b>smaller</b> than the euclidean distance between
     * the coordinates of the two {@link Location} endpoints.
     */
    public Road(RoadType type, double length, Location firstEndpoint, Location secondEndpoint) throws InvalidLength {
        if (length < Utils.getEucDistance(firstEndpoint, secondEndpoint)) {
            throw new InvalidLength();
        }
        this.type = type;
        this.length = length;
        this.firstEndpoint = firstEndpoint;
        this.secondEndpoint = secondEndpoint;
    }

    /**
     * Constructs a road between two {@link Location} objects.
     * @param type The type of the newly created road
     * @param length The length of the newly created road
     * @param speedLimit The speed limit on the newly created road
     * @param firstEndpoint One of the endpoints of the road
     * @param secondEndpoint The other endpoint of the road
     * @throws InvalidLength If the {@code length} of the road is <b>smaller</b> than the euclidean distance between
     * the coordinates of the two {@link Location} endpoints.
     */
    public Road(RoadType type, double length, int speedLimit, Location firstEndpoint, Location secondEndpoint) throws InvalidLength {
        this(type, length, firstEndpoint, secondEndpoint);
        this.speedLimit = speedLimit;
    }

    public RoadType getType() {
        return type;
    }

    public void setType(RoadType type) {
        this.type = type;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Location getFirstEndpoint() {
        return firstEndpoint;
    }

    public void setFirstEndpoint(Location firstEndpoint) {
        this.firstEndpoint = firstEndpoint;
    }

    public Location getSecondEndpoint() {
        return secondEndpoint;
    }

    public void setSecondEndpoint(Location secondEndpoint) {
        this.secondEndpoint = secondEndpoint;
    }

    @Override
    public String toString() {
        return "Road{" +
                "type=" + type +
                ", length=" + length +
                ", speedLimit=" + speedLimit +
                ", firstEndpoint=" + firstEndpoint +
                ", secondEndpoint=" + secondEndpoint +
                '}';
    }
}