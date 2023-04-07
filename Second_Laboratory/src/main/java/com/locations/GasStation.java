package com.locations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The {@code GasStation} class represents different gas stations placed on the cartesian coordinate system.
 * Besides the attributes inherited from the {@code Location} class, it also stores the price of gas.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class GasStation extends Location {
    private double gasPrice;

    /**
     * Initializes a new {@code GasStation} object, having a specified name, cartesian coordinates and price of gas.
     *
     * @param name     The name of the newly created gas station
     * @param x        The x-coordinate in the Cartesian Coordinate System
     * @param y        The y-coordinate in the Cartesian Coordinate System
     * @param gasPrice The price of gas in this station
     */
    public GasStation(String name, int x, int y, double gasPrice) {
        super(name, x, y);
        this.gasPrice = gasPrice;
    }

    /**
     * Initializes a new {@code GasStation} object, placed on (0, 0) cartesian coordinates having a specified name and price of gas.
     *
     * @param name     The name of the newly created location
     * @param gasPrice The price of gas in this station
     */
    public GasStation(String name, double gasPrice) {
        super(name, 0, 0);
        this.gasPrice = gasPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GasStation) {
            GasStation gasStation = (GasStation) o;
            return (super.equals(o) && this.gasPrice == gasStation.gasPrice);
        }
        return false;
    }
}
