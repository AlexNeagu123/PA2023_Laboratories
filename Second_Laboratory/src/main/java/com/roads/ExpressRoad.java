package com.roads;

import com.exceptions.InvalidEndpoints;
import com.exceptions.InvalidLength;
import com.locations.Location;
import lombok.ToString;

/**
 * The {@code ExpressRoad} class represents an express road that connects to {@link Location} objects.
 */
@ToString(callSuper = true)
public class ExpressRoad extends Road {
    /**
     * Constructs an express road between two {@link Location} objects.
     *
     * @param length         The length of the newly created express road
     * @param firstEndpoint  One of the endpoints of the express road
     * @param secondEndpoint The other endpoint of the express road
     * @param speedLimit     The speed limit on the newly created express road
     * @throws InvalidLength    If the {@code length} of the express road is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@link Location} endpoints
     * @throws InvalidEndpoints If the endpoints are <b>the same</b>
     */
    public ExpressRoad(double length, int speedLimit, Location firstEndpoint, Location secondEndpoint) throws InvalidLength, InvalidEndpoints {
        super(length, firstEndpoint, secondEndpoint);
        this.speedLimit = speedLimit;
    }

    /**
     * Constructs an express road between two {@link Location} objects with a default speed limit of 120km/h.
     *
     * @param length         The length of the newly created express road
     * @param firstEndpoint  One of the endpoints of the express road
     * @param secondEndpoint The other endpoint of the express road
     * @throws InvalidLength    If the {@code length} of the express road is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@link Location} endpoints
     * @throws InvalidEndpoints If the endpoints are <b>the same</b>
     */
    public ExpressRoad(double length, Location firstEndpoint, Location secondEndpoint) throws InvalidLength, InvalidEndpoints {
        super(length, firstEndpoint, secondEndpoint);
        this.speedLimit = 120;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ExpressRoad) {
            return super.equals(o);
        }
        return false;
    }
}
