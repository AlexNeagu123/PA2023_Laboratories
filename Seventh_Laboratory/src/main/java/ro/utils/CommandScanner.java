package ro.utils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.app.TimeKeeper;
import ro.exceptions.InvalidEdgeCountException;
import ro.exceptions.InvalidTimeLimitException;
import ro.game.explorations.Exploration;
import ro.game.explorations.GraphExploration;
import ro.game.explorations.MatrixExploration;

import java.util.Scanner;

/**
 * The <tt>CommandScanner</tt> class is responsible for reading commands typed in the terminal by the user.
 * <p>
 * Also, this class provides several methods that repeatedly ask the user to introduce specific data, until the data
 * introduced is valid.
 */
@AllArgsConstructor
@Log4j2
public class CommandScanner {
    private Scanner scanner;

    /**
     * @return A line of text is read from the terminal
     */
    public String readCommand() {
        String commandText = "";
        while (commandText.length() == 0) {
            commandText = scanner.nextLine();
        }
        return commandText;
    }

    /**
     * Repeatedly asks the user to introduce an upper limit of seconds to run the algorithm
     * <t>
     * When the data introduced by the user is correct, a {@link TimeKeeper} object is created
     *
     * @return The {@link TimeKeeper} object created
     */
    public TimeKeeper collectTimeKeeper() {
        System.out.println("Please enter the maximum number of seconds you want to wait for the exploration");
        while (true) {
            try {
                String strNumber = readCommand();
                int seconds = Integer.parseInt(strNumber);
                return new TimeKeeper(seconds);
            } catch (NumberFormatException nfe) {
                System.out.println("Please, enter an integer number of seconds!");
            } catch (InvalidTimeLimitException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Repeatedly asks the user to introduce the exploration type he wants
     *
     * @return <tt>true</tt> if the user chose the matrix exploration type, <tt>false</tt>
     * if the user chose the graph exploration type
     */
    public boolean extractExplorationType() {
        boolean isMatrix = false;
        while (true) {
            String explorationType = readCommand();
            if (explorationType.equals("matrix")) {
                isMatrix = true;
                break;
            }
            if (explorationType.equals("graph")) {
                break;
            }
            System.out.println("Please enter a valid word: 'graph' or 'matrix'");
        }
        return isMatrix;
    }

    /**
     * Repeatedly asks the user to introduce the number of nodes he wants the {@link ro.game.maps.GraphMap} to have,
     * until a valid integer is introduced
     *
     * @return The number of nodes introduced by the user
     */
    public int extractNodeCount() {
        int nodeCount;
        while (true) {
            try {
                String strNumber = readCommand();
                nodeCount = Integer.parseInt(strNumber);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("Please, enter an integer number!");
            }
        }
        return nodeCount;
    }

    /**
     * Repeatedly asks the user to introduce the number of edges he wants the {@link ro.game.maps.GraphMap} to have,
     * until a valid integer, that respects the boundaries set by the number of nodes, is introduced
     *
     * @return The number of edges introduced by the user
     */
    public int extractEdgeCount(int nodeCount) {
        int edgeCount;
        while (true) {
            try {
                String strNumber = readCommand();
                edgeCount = Integer.parseInt(strNumber);
                if (edgeCount < 0 && edgeCount > nodeCount * (nodeCount - 1) / 2) {
                    throw new InvalidEdgeCountException(nodeCount);
                }
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("Please, enter an integer number!");
            } catch (InvalidEdgeCountException e) {
                System.out.println(e.getMessage());
            }
        }
        return edgeCount;
    }

    /**
     * Asks the user a number of questions in order to choose between {@link MatrixExploration} or {@link GraphExploration}
     *
     * @param timeKeeper A {@link TimeKeeper} object - a daemon that will count the number of seconds in the exploration
     * @return The {@link Exploration} object configured by the user
     */
    public Exploration collectExploration(TimeKeeper timeKeeper) {
        System.out.println("Please choose the exploration type you want: 'matrix' or 'graph'");
        boolean isMatrix = extractExplorationType();

        if (isMatrix) {
            System.out.println("Please enter the length of the matrix");
        } else {
            System.out.println("Please enter the number of nodes in the graph");
        }

        int nodeCount = extractNodeCount();
        if (isMatrix) {
            return new MatrixExploration(timeKeeper, nodeCount);
        }

        System.out.println("Please enter the number of edges in the graph");
        int edgeCount = extractEdgeCount(nodeCount);
        return new GraphExploration(timeKeeper, nodeCount, edgeCount);
    }
}
