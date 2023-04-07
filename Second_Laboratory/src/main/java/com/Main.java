package com;

import com.exceptions.InvalidEndpoints;
import com.exceptions.InvalidLength;
import com.exceptions.InvalidQuery;
import com.locations.Location;
import com.problem.Problem;
import com.problem.algorithms.DijkstraSolution;
import com.testing.InstanceReader;
import com.exceptions.InvalidProblem;
import com.problem.algorithms.DFSAlgorithm;
import com.problem.algorithms.DijkstraAlgorithm;
import com.testing.InstanceGenerator;

import java.io.IOException;

/**
 * Homework and Bonus implementation from Laboratory 2.
 * <p>
 * Two instances of the shortest path problem are solved. One of the instance is custom-made, and the other is randomly generated (Bonus).
 * <p>
 * The custom-made instance is read from a file stored in testing/testcases/small_test.txt
 * <p>
 * For each problem instance, a starting and an ending location are chosen (either manually or randomly)
 * <p>
 * A DFS is performed on the graph (made of locations and roads) to check whether there exists a path between the starting and
 * ending locations. (Homework)
 * <p>
 * If a path exists, the Dijkstra's Algorithm is performed on the graph and finds the shortest path between starting and ending
 * locations. (Bonus)
 */
public class Main {
    public static void main(String[] args) {
        try {

            Runtime runtime = Runtime.getRuntime();
            long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
            long initialTime = System.currentTimeMillis();

            runCustomInstance("src/main/java/com/testing/testcases/small_test.txt");
            runGeneratedInstance();

            long runningTime = System.currentTimeMillis() - initialTime;
            long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
            long memoryIncrease = usedMemoryAfter - usedMemoryBefore;

            System.out.println("The running time was " + runningTime + "ms");
            System.out.println("The memory increased by " + memoryIncrease + " bytes");

        } catch (InvalidLength | InvalidEndpoints | InvalidProblem | InvalidQuery | IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * A custom-made instance is read from a file. It is checked if a path exists between two fixed locations.
     * If it does, Dijkstra's Algorithm is performed to find the shortest path between the two fixed locations.
     *
     * @param fileName The name of the file where the custom instance is stored
     * @throws InvalidLength    If the {@code length} of some road is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@code Location} endpoints
     * @throws InvalidEndpoints If the endpoints of some road are <b>the same</b>
     * @throws IOException      If the reading from the file fails
     * @throws InvalidProblem   If the roads given in the {@code Problem} contains endpoints that are not stored in the locations array
     * @throws InvalidQuery     If one of the locations (or both) are <b>not found</b> in the problem instance
     */
    public static void runCustomInstance(String fileName) throws InvalidLength, InvalidEndpoints, IOException, InvalidProblem, InvalidQuery {
        Problem problemInstance = InstanceReader.getProblemInstance(fileName);
        System.out.println("A custom instance read from file " + fileName);

        Location startingLocation = problemInstance.getLocationOnIndex(1);
        Location endingLocation = problemInstance.getLocationOnIndex(4);

        DFSAlgorithm dfsAlgorithm = new DFSAlgorithm(problemInstance);

        System.out.print("A path between " + startingLocation + " and " + endingLocation + " ");
        System.out.println(dfsAlgorithm.pathExists(startingLocation, endingLocation) ? "exists!" : "doesn't exist!");

        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(problemInstance);
        DijkstraSolution dijkstraSolution = dijkstraAlgorithm.computeShortestPath(startingLocation, endingLocation);

        printSolution(dijkstraSolution, startingLocation, endingLocation);
    }

    /**
     * An instance is randomly generated. It is checked if a path exists between two randomly chosen locations.
     * If it does, Dijkstra's Algorithm is performed to find the shortest path between the two randomly chosen locations.
     *
     * @throws InvalidLength    If the {@code length} of some road is <b>smaller</b> than the euclidean distance between
     *                          the coordinates of the two {@code Location} endpoints
     * @throws InvalidEndpoints If the endpoints of some road are <b>the same</b>
     * @throws InvalidProblem   If the roads given in the {@code Problem} contains endpoints that are not stored in the locations array
     * @throws InvalidQuery     If one of the locations (or both) are <b>not found</b> in the problem instance
     */
    public static void runGeneratedInstance() throws InvalidLength, InvalidEndpoints, InvalidProblem, InvalidQuery {
        Problem problemInstance = InstanceGenerator.generateProblemInstance();
        int locationCount = problemInstance.getLocationCount();

        System.out.println("A random generated instance with " + locationCount + " locations and " + problemInstance.getRoadCount() + " roads.");

        int startingLocationIndex = (int) (Math.random() * (locationCount - 1));
        int endingLocationIndex = startingLocationIndex;
        while (endingLocationIndex == startingLocationIndex) {
            endingLocationIndex = (int) (Math.random() * (locationCount - 1));
        }

        Location startingLocation = problemInstance.getLocationOnIndex(startingLocationIndex);
        Location endingLocation = problemInstance.getLocationOnIndex(endingLocationIndex);

        DFSAlgorithm dfsAlgorithm = new DFSAlgorithm(problemInstance);
        boolean pathExists = dfsAlgorithm.pathExists(startingLocation, endingLocation);

        System.out.print("A path between " + startingLocation + " and " + endingLocation + " ");
        System.out.println(pathExists ? "exists!" : "doesn't exist!");

        if (pathExists) {
            DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(problemInstance);
            DijkstraSolution dijkstraSolution = dijkstraAlgorithm.computeShortestPath(startingLocation, endingLocation);
            printSolution(dijkstraSolution, startingLocation, endingLocation);
        }
    }

    public static void printSolution(DijkstraSolution dijkstraSolution, Location startLocation, Location endLocation) {
        System.out.printf("The shortest path from %s to %s consists of the following roads:\n", startLocation.getName(), endLocation.getName());
        for (int i = 0; i < dijkstraSolution.roadsTravelled.length; ++i) {
            System.out.println(dijkstraSolution.roadsTravelled[i]);
        }
        System.out.printf("The total distance is: %f\n", dijkstraSolution.computeLength());
    }
}
