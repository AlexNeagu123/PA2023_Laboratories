package com.lab1.homework;

/**
 * The {@code LatinSquare} class is able to build and display a Latin Square.
 * <p>
 * A Latin Square is an n x n array filled with n different symbols, each occurring exactly once in a row and
 * exactly once in a column. The symbols used in this class are natural numbers from 1 to n.
 *
 * @author alexneagu
 */

public class LatinSquare {
    private final int squareLen;
    private final int[][] square;
    public static final int MAXIMUM_DISPLAY_LENGTH = 30000;

    /**
     * Constructs a Latin Square with dimensions specified by the {@code squareLen} argument.
     *
     * @param squareLen The length of each square side
     */
    public LatinSquare(int squareLen) {
        this.squareLen = squareLen;
        this.square = new int[squareLen][squareLen];
        for (int row = 0; row < squareLen; ++row) {
            for (int col = 0; col < squareLen; ++col) {
                int cellElement = (col + row) % squareLen;
                this.square[row][col] = cellElement;
            }
        }
    }

    /**
     * Iterates through every row of the square, concatenates all the characters from the current row,
     * and displays the result on the screen on each iteration.
     * <p>
     * If the square side length is greater than 30000, the results are not displayed on the screen.
     */
    public void displayRows() {
        if (squareLen < MAXIMUM_DISPLAY_LENGTH) {
            System.out.println("-----Row concatenation-----");
        }
        for (int row = 0; row < squareLen; ++row) {
            StringBuilder rowAsString = concatenateRow(row);
            if (squareLen < MAXIMUM_DISPLAY_LENGTH) {
                System.out.printf("Row %d: %s\n", row, rowAsString);
            }
        }
    }

    /**
     * Iterates through every column of the square, concatenates all the characters from the current column,
     * and displays the result on the screen on each iteration.
     * <p>
     * If the square side length is greater than 30000, the results are not displayed on the screen.
     */
    public void displayColumns() {
        if (squareLen < MAXIMUM_DISPLAY_LENGTH) {
            System.out.println("-----Column concatenation-----");
        }
        for (int col = 0; col < squareLen; ++col) {
            StringBuilder columnAsString = concatenateColumn(col);
            if (squareLen < MAXIMUM_DISPLAY_LENGTH) {
                System.out.printf("Column %d: %s\n", col, columnAsString);
            }
        }
    }

    /**
     * Concatenates all elements from the row given by the argument {@code row}.
     *
     * @param row The row to concatenate
     * @return A StringBuilder object representation of the result
     */
    StringBuilder concatenateRow(int row) {
        StringBuilder rowConcat = new StringBuilder();
        for (int col = 0; col < squareLen; ++col) {
            rowConcat.append(square[row][col]);
        }
        return rowConcat;
    }

    /**
     * Concatenates all elements from the column given by the argument {@code col}.
     *
     * @param col The column to concatenate
     * @return A {@code StringBuilder} object representation of the result
     */
    StringBuilder concatenateColumn(int col) {
        StringBuilder colConcat = new StringBuilder();
        for (int row = 0; row < squareLen; ++row) {
            colConcat.append(square[row][col]);
        }
        return colConcat;
    }
}