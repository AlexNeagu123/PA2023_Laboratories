package ro.utils;

import ro.game.maps.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * The <tt>Movement</tt> class contains useful methods for moving in a {@link ro.game.maps.MatrixMap}.
 * <p>
 * Only four directions are available: North, South, East and West.
 */
public class Movement {
    private static final int[] dx = new int[]{0, 0, -1, 1};
    private static final int[] dy = new int[]{1, -1, 0, 0};

    /**
     * Checks if a neighbouring cell is inside the matrix border
     *
     * @param coordinates  The {@link Coordinates} of the neighbour
     * @param matrixLength The length of the matrix
     * @return <tt>true</tt> if the neighbour is inside the matrix border, <tt>false otherwise</tt>
     */
    private static boolean isValidNeighbour(Coordinates coordinates, int matrixLength) {
        return (coordinates.getRow() >= 0 && coordinates.getColumn() >= 0 &&
                coordinates.getRow() < matrixLength && coordinates.getColumn() < matrixLength);
    }

    /**
     * @param coordinates  The {@link Coordinates} of a matrix cell
     * @param matrixLength The length of the matrix
     * @return The list of neighbours of the cell that are inside the matrix border
     */
    public static List<Integer> getValidNeighbours(Coordinates coordinates, int matrixLength) {
        List<Integer> validNeighbours = new ArrayList<>();
        for (int direction = 0; direction < 4; direction++) {
            Coordinates neighbour = new Coordinates(coordinates.getRow() + dx[direction], coordinates.getColumn() + dy[direction]);
            if (isValidNeighbour(neighbour, matrixLength)) {
                validNeighbours.add(NodeUtils.mapCoordinatesToNode(neighbour, matrixLength));
            }
        }
        return validNeighbours;
    }
}
