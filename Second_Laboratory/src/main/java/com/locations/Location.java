package com.locations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The {@code Location} class provides a skeletal implementation of the {@code City}, {@code Airport} and {@code GasStation}
 * classes.
 * <p>
 * Components such as name and the cartesian coordinates of a location are stored in this class.
 *
 * @author alexneagu
 */

@Getter
@Setter
@ToString
public abstract class Location {
    protected String name;
    protected double x;
    protected double y;

    /**
     * Constructs a new {@code Location} object of type CITY, having a specified name and coordinates.
     *
     * @param name The name of the newly created location
     * @param x    The x-coordinate in the Cartesian Coordinate System
     * @param y    The y-coordinate in the Cartesian Coordinate System
     */
    Location(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && this.getClass() == o.getClass()) {
            Location location = (Location) o;
            return (this.x == location.x && this.y == location.y && this.name.equals(location.name));
        }
        return false;
    }

}
