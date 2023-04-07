package com.testing;

import com.exceptions.InvalidEndpoints;
import com.exceptions.InvalidLength;
import com.locations.City;
import com.problem.Problem;
import com.roads.HighwayRoad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is able to read a problem instances from a text file.
 * <p>
 * All the locations created are {@link City} objects and all the roads are {@link HighwayRoad} objects.
 * <p>
 * The test files must follow the following pattern:
 * locationCount
 * {@code locationCount} lines of type: name x y populationCount
 * roadCount
 * {@code roadCount} lines of type: length index_1 index_2
 */
public class InstanceReader {
    /**
     * @param fileName The name of the file where the problem instance is stored
     * @return A {@link Problem} object that describes the problem instance that has been read
     * @throws IOException      If the reading from the file fails
     * @throws InvalidLength    If the {@code length} of some road is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@code Location} endpoints
     * @throws InvalidEndpoints If the endpoints of some road are <b>the same</b>
     */
    public static Problem getProblemInstance(String fileName) throws IOException, InvalidLength, InvalidEndpoints {
        Problem problemInstance = new Problem();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

        // Adding Locations
        String locationCountStr = bufferedReader.readLine();
        int locationCount = Integer.parseInt(locationCountStr);

        for (int i = 0; i < locationCount; ++i) {
            String[] line = bufferedReader.readLine().split(" ");
            String cityName = line[0];
            int x = Integer.parseInt(line[1]);
            int y = Integer.parseInt(line[2]);
            int populationCount = Integer.parseInt(line[3]);
            problemInstance.addLocation(new City(cityName, x, y, populationCount));
        }

        // Adding Roads
        String roadCountStr = bufferedReader.readLine();
        int roadCount = Integer.parseInt(roadCountStr);

        for (int i = 0; i < roadCount; ++i) {
            String[] line = bufferedReader.readLine().split(" ");
            double length = Double.parseDouble(line[0]);
            int firstLocIndex = Integer.parseInt(line[1]);
            int secondLocIndex = Integer.parseInt(line[2]);
            problemInstance.addRoad(new HighwayRoad(length, problemInstance.getLocationOnIndex(firstLocIndex), problemInstance.getLocationOnIndex(secondLocIndex)));
        }

        return problemInstance;
    }
}
