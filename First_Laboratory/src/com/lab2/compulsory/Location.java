package com.lab2.compulsory;

import com.lab2.compulsory.enums.LocationType;

/**
 * The {@code Location} class represents a location in a cartesian coordinate system, that is characterized by a name,
 * type (CITY, AIRPORT or GAS_STATION) and two real numbers (x and y coordinates).
 */
public class Location {
    private String name;
    private LocationType type = LocationType.CITY;
    private double x = 0;
    private double y = 0;

    /**
     * Initializes a new {@code Location} object of type {@code CITY}, placed on cartesian coordinates (0, 0),
     * having a specified name.
     *
     * @param name The name of the newly created location
     */
    public Location(String name) {
        this.name = name;
    }

    /**
     * Initializes a new {@code Location} object, placed on cartesian coordinates (0, 0),
     * having a specified name and type.
     *
     * @param name The name of the newly created location
     * @param type The type of the newly created location
     */
    public Location(String name, LocationType type) {
        this(name);
        this.type = type;
    }

    /**
     * Initializes a new {@code Location} object of type CITY, having a specified name and coordinates.
     *
     * @param name The name of the newly created location
     * @param x    The x-coordinate in the Cartesian Coordinate System
     * @param y    The y-coordinate in the Cartesian Coordinate System
     */
    public Location(String name, double x, double y) {
        this(name);
        this.x = x;
        this.y = y;
    }

    /**
     * Initializes a new {@code Location} object, having a specified name, type and coordinates.
     *
     * @param name The name of the newly created location
     * @param type The type of the newly created location
     * @param x    The x-coordinate in the Cartesian Coordinate System
     * @param y    The y-coordinate in the Cartesian Coordinate System
     */
    public Location(String name, LocationType type, double x, double y) {
        this(name, type);
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}