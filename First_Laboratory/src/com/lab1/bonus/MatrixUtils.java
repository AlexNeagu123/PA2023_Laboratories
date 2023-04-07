package com.lab1.bonus;

/**
 * The {@code MatrixUtils} class has useful static methods for multiplying two matrices and
 * displaying a matrix on the screen with a specific message.
 *
 * @author alexneagu
 */
public class MatrixUtils {
    /**
     * Multiplies two matrices.
     *
     * @param A The first matrix from the product
     * @param B The second matrix from the product
     * @return A matrix representing the result of the product
     * @throws IllegalArgumentException If it's impossible to multiply the matrices
     */
    public static int[][] multiply(int[][] A, int[][] B) throws IllegalArgumentException {
        int rowsA = A.length;
        int columnsA = A[0].length;
        int rowsB = B.length;
        int columnsB = B[0].length;

        if (columnsA != rowsB) {
            throw new IllegalArgumentException("Number of columns in A should be equal to the number of rows in B");
        }

        int[][] C = new int[rowsA][columnsB];
        for (int row = 0; row < rowsA; ++row) {
            for (int col = 0; col < columnsB; ++col) {
                for (int i = 0; i < columnsA; ++i) {
                    C[row][col] += A[row][i] * B[i][col];
                }
            }
        }
        return C;
    }

    /**
     * Assuming that a matrix was obtained from an exponentiation,
     * displays the matrix on the screen along with an informative message.
     *
     * @param pow    The exponent of an initial matrix that generated {@code matrix} as a result.
     * @param matrix The matrix to display on the screen
     */
    public static void printPoweredMatrix(int pow, int[][] matrix) {
        System.out.println("Matrix raised to power " + pow + ":");
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.printf("%d ", anInt);
            }
            System.out.println();
        }
        System.out.println();
    }
}