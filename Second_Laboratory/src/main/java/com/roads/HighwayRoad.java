package com.roads;

import com.exceptions.InvalidEndpoints;
import com.exceptions.InvalidLength;
import com.locations.Location;
import lombok.ToString;

/**
 * The {@code HighwayRoad} class represents a highway that connects to {@link Location} objects.
 */
@ToString(callSuper = true)
public class HighwayRoad extends Road {
    /**
     * Constructs a highway between two {@link Location} objects.
     *
     * @param length         The length of the newly created highway
     * @param firstEndpoint  One of the endpoints of the highway
     * @param secondEndpoint The other endpoint of the highway
     * @param speedLimit     The speed limit on the newly created highway
     * @throws InvalidLength    If the {@code length} of the highway is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@link Location} endpoints
     * @throws InvalidEndpoints If the endpoints are <b>the same</b>
     */
    public HighwayRoad(double length, int speedLimit, Location firstEndpoint, Location secondEndpoint) throws InvalidLength, InvalidEndpoints {
        super(length, firstEndpoint, secondEndpoint);
        this.speedLimit = speedLimit;
    }

    /**
     * Constructs a highway between two {@link Location} objects with a default speed limit of 80km/h.
     *
     * @param length         The length of the newly created highway
     * @param firstEndpoint  One of the endpoints of the highway
     * @param secondEndpoint The other endpoint of the highway
     * @throws InvalidLength    If the {@code length} of the highway is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@link Location} endpoints
     * @throws InvalidEndpoints If the endpoints are <b>the same</b>
     */
    public HighwayRoad(double length, Location firstEndpoint, Location secondEndpoint) throws InvalidLength, InvalidEndpoints {
        super(length, firstEndpoint, secondEndpoint);
        this.speedLimit = 80;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HighwayRoad) {
            return super.equals(o);
        }
        return false;
    }
}
