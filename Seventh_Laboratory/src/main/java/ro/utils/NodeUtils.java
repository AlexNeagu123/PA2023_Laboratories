package ro.utils;

import ro.game.maps.Coordinates;

/**
 * The <tt>NodeUtils</tt> command provides useful methods for converting the {@link Coordinates} of a matrix cell into
 * an {@link Integer} id.
 */
public class NodeUtils {
    /**
     * @param coordinates The {@link Coordinates} of a matrix cell
     * @param length      The length of the matrix
     * @return An {@link Integer} id mapped by the coordinates
     */
    public static Integer mapCoordinatesToNode(Coordinates coordinates, int length) {
        return coordinates.getRow() * length + coordinates.getColumn();
    }

    /**
     * @param nodeId An {@link Integer} id
     * @param length The length of the matrix
     * @return The {@link Coordinates} mapped by the {@code id}
     */
    public static Coordinates mapNodeToCoordinates(Integer nodeId, int length) {
        return new Coordinates(nodeId / length, nodeId % length);
    }
}
