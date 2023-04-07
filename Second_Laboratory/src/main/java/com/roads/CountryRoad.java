package com.roads;

import com.exceptions.InvalidEndpoints;
import com.exceptions.InvalidLength;
import com.locations.Location;
import lombok.ToString;

/**
 * The {@code CountryRoad} class represents a country road that connects to {@link Location} objects.
 */

@ToString(callSuper = true)
public class CountryRoad extends Road {
    /**
     * Constructs a country road between two {@link Location} objects.
     *
     * @param length         The length of the newly created country road
     * @param firstEndpoint  One of the endpoints of the country road
     * @param secondEndpoint The other endpoint of the country road
     * @param speedLimit     The speed limit on the newly created country road
     * @throws InvalidLength    If the {@code length} of the country road is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@link Location} endpoints
     * @throws InvalidEndpoints If the endpoints are <b>the same</b>
     */
    public CountryRoad(double length, int speedLimit, Location firstEndpoint, Location secondEndpoint) throws InvalidLength, InvalidEndpoints {
        super(length, firstEndpoint, secondEndpoint);
        this.speedLimit = speedLimit;
    }

    /**
     * Constructs a country road between two {@link Location} objects with a default speed limit of 50km/h.
     *
     * @param length         The length of the newly created country road
     * @param firstEndpoint  One of the endpoints of the country road
     * @param secondEndpoint The other endpoint of the country road
     * @throws InvalidLength    If the {@code length} of the country road is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@link Location} endpoints
     * @throws InvalidEndpoints If the endpoints are <b>the same</b>
     */
    public CountryRoad(double length, Location firstEndpoint, Location secondEndpoint) throws InvalidLength, InvalidEndpoints {
        super(length, firstEndpoint, secondEndpoint);
        this.speedLimit = 50;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CountryRoad) {
            return super.equals(o);
        }
        return false;
    }
}
