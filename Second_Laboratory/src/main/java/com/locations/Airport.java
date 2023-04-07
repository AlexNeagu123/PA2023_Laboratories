package com.locations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The {@code Airport} class represents different airports placed on the cartesian coordinate system.
 * Besides the attributes inherited from the {@code Location} class, it also stores the number of terminals.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class Airport extends Location {
    private int numberOfTerminals;

    /**
     * Initializes a new {@code Airport} object, having a specified name, cartesian coordinates and a number of terminals.
     *
     * @param name              The name of the newly created airport
     * @param x                 The x-coordinate in the Cartesian Coordinate System
     * @param y                 The y-coordinate in the Cartesian Coordinate System
     * @param numberOfTerminals The number of inhabitants
     */
    public Airport(String name, int x, int y, int numberOfTerminals) {
        super(name, x, y);
        this.numberOfTerminals = numberOfTerminals;
    }

    /**
     * Initializes a new {@code Airport} object, having a specified name, cartesian coordinates and a number of terminals.
     *
     * @param name              The name of the newly created airport
     * @param numberOfTerminals The number of inhabitants
     */
    public Airport(String name, int numberOfTerminals) {
        super(name, 0, 0);
        this.numberOfTerminals = numberOfTerminals;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Airport) {
            Airport airport = (Airport) o;
            return (super.equals(o) && this.numberOfTerminals == airport.numberOfTerminals);
        }
        return false;
    }
}
