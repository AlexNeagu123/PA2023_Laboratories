package com.lab1.bonus;

/**
 * Bonus Implementation from Laboratory 1.
 * <p>
 * One or two non-negative integers are expected from the command line.
 * <p>
 * Several validations are made on the arguments received from the command line.
 * <p>
 * If the arguments received violates the restrictions, an {@code IllegalArgumentException} is thrown.
 * <p>
 * If only one argument is passed from the command line, the first bullet is solved.
 * <p>
 * If two arguments are passed from the command line, the second bullet is solved.
 *
 * @author alexneagu
 */
public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 1 && args.length != 2) {
                throw new IllegalArgumentException("One or two arguments should be passed!");
            }

            int nodeNr = Integer.parseInt(args[0]);
            if (nodeNr <= 0) {
                throw new IllegalArgumentException("The first argument should be a strictly positive integer!");
            }

            if (args.length == 1) {
                // only one argument passed => solve the first bullet
                CyclicGraphSolver.run(nodeNr);
            } else {
                // two arguments passed => solve the second bullet
                int kIndex = Integer.parseInt(args[1]);
                if (kIndex < 0) {
                    throw new IllegalArgumentException("The second argument should be a non-negative integer!");
                }
                RegularGraphSolver.run(nodeNr, kIndex);
            }
        } catch (NumberFormatException err) {
            System.out.println("Incorrect type of the argument. Integer expected.");
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}