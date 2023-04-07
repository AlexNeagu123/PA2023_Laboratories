package com.lab1.homework;

/**
 * Homework Implementation from Laboratory 1.
 * <p>
 * A strictly positive integer value is expected from the command line.
 * <p>
 * Several validations are made on the arguments received from the command line.
 * <p>
 * If the arguments received violates the restrictions, an {@code IllegalArgumentException} is thrown.
 * <p>
 * A latinSquare object with a given size is instantiated.
 * <p>
 * The values from rows and columns are displayed on the screen concatenated.
 * <p>
 * The running time is displayed (in Nanoseconds) at the end.
 */

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("Exactly one argument should be passed!");
            }

            int squareLen = Integer.parseInt(args[0]);
            if (squareLen <= 0) {
                throw new IllegalArgumentException("The argument should be a strictly positive integer!");
            }

            long beginTime = System.nanoTime();

            LatinSquare latinSquare = new LatinSquare(squareLen);
            latinSquare.displayRows();
            latinSquare.displayColumns();

            long endTime = System.nanoTime();
            System.out.printf("Running time is: %d\n", endTime - beginTime);

        } catch (NumberFormatException exception) {
            System.out.println("The argument should be of type integer!");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}