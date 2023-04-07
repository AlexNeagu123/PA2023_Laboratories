package com.roads;

import com.exceptions.InvalidEndpoints;
import com.exceptions.InvalidLength;
import com.locations.Location;
import com.math.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * The {@code Road} class provides a skeletal implementation of the {@link HighwayRoad}, {@link ExpressRoad}
 * and {@link CountryRoad} classes.
 * It represents a road that connects two distinct {@link Location} objects.
 */
@Getter
@Setter
@ToString
public abstract class Road {
    protected double length;
    protected int speedLimit;
    protected Location firstEndpoint;
    protected Location secondEndpoint;

    /**
     * Constructs a road between two {@link Location} objects.
     * @param length         The length of the newly created road
     * @param firstEndpoint  One of the endpoints of the road
     * @param secondEndpoint The other endpoint of the road
     * @throws InvalidLength    If the {@code length} of the road is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@link Location} endpoints
     * @throws InvalidEndpoints If the endpoints are <b>the same</b>
     */
    Road(double length, Location firstEndpoint, Location secondEndpoint) throws InvalidLength, InvalidEndpoints {
        if (length < Utils.getEucDistance(firstEndpoint, secondEndpoint)) {
            throw new InvalidLength();
        }
        if (firstEndpoint.equals(secondEndpoint)) {
            throw new InvalidEndpoints();
        }
        this.length = length;
        this.firstEndpoint = firstEndpoint;
        this.secondEndpoint = secondEndpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && this.getClass() == o.getClass()) {
            Road road = (Road) o;
            return (this.length == road.length
                    && this.speedLimit == road.speedLimit
                    && Objects.equals(this.firstEndpoint, road.firstEndpoint)
                    && Objects.equals(this.secondEndpoint, road.secondEndpoint));
        }
        return false;
    }

}
