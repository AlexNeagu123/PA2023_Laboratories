package com.lab2.compulsory;

import com.lab2.compulsory.enums.LocationType;
import com.lab2.compulsory.enums.RoadType;
import com.lab2.compulsory.exceptions.InvalidLength;

/**
 * Compulsory Implementation From Laboratory 2.
 * <p>
 * 6 instances of the Location Class are created.
 * <p>
 * Different constructors are used.
 * <p>
 * 3 instances of the Road Class are created. One of them throws an exception because of its length.
 * <p>
 * All objects are successfully printed on the screen, because the toString() method was overloaded in both Location and
 * Road classes.
 */
public class Main {
    public static void main(String[] args) {
        Location firstCity = new Location("Chisinau", LocationType.CITY, 0.5, 5.0);
        Location secondCity = new Location("Bucharest", LocationType.CITY, 99.5, 100);
        Location firstAirport = new Location("WizzAir", LocationType.AIRPORT, 10, 20);
        Location firstGasStation = new Location("Petrom", LocationType.GAS_STATION, 5, 4);
        Location thirdCity = new Location("Nisporeni");
        Location secondAirport = new Location("Tarom", LocationType.AIRPORT);

        System.out.println(firstCity);
        System.out.println(secondCity);
        System.out.println(firstAirport);
        System.out.println(firstGasStation);
        System.out.println(thirdCity);
        System.out.println(secondAirport);

        try {
            Road firstRoad = new Road(RoadType.HIGHWAY, 250, firstCity, secondCity); // should be created
            System.out.println(firstRoad);
            Road secondRoad = new Road(RoadType.COUNTRY, 50, 50, firstCity, firstAirport); // should be created
            System.out.println(secondRoad);
            Road thirdRoad = new Road(RoadType.COUNTRY, 10, thirdCity, secondCity); // should throw exception
            System.out.println(thirdRoad);
        } catch (InvalidLength exception) {
            System.out.println(exception.getMessage());
        }
    }
}