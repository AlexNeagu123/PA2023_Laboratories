package com.locations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The {@code City} class represents different cities placed on the cartesian coordinate system.
 * Besides the attributes inherited from the {@code Location} class, it also stores the population count.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class City extends Location {
    private int populationCount;

    /**
     * Initializes a new {@code City} object, having a specified name, cartesian coordinates and a population count.
     *
     * @param name            The name of the newly created city
     * @param x               The x-coordinate in the Cartesian Coordinate System
     * @param y               The y-coordinate in the Cartesian Coordinate System
     * @param populationCount The number of inhabitants
     */
    public City(String name, double x, double y, int populationCount) {
        super(name, x, y);
        this.populationCount = populationCount;
    }

    /**
     * Initializes a new {@code City} object, placed on (0, 0) cartesian coordinates having a specified name and population count.
     *
     * @param name            The name of the newly created city
     * @param populationCount The number of inhabitants
     */
    public City(String name, int populationCount) {
        super(name, 0, 0);
        this.populationCount = populationCount;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof City) {
            City city = (City) o;
            return (super.equals(o) && this.populationCount == city.populationCount);
        }
        return false;
    }
}
