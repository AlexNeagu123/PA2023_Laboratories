package com.math;

import com.locations.Location;

public class Utils {
    /**
     * Compute the euclidean distances between two cartesian points.
     *
     * @param x1 The x-coordinate of the first point.
     * @param y1 The y-coordinate of the first point.
     * @param x2 The x-coordinate of the second point.
     * @param y2 The y-coordinate of the second point.
     * @return The euclidean distance between the points.
     */
    public static double getEucDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double getEucDistance(Location firstEndpoint, Location secondEndpoint) {
        return getEucDistance(firstEndpoint.getX(), firstEndpoint.getY(), secondEndpoint.getX(), secondEndpoint.getY());
    }
}