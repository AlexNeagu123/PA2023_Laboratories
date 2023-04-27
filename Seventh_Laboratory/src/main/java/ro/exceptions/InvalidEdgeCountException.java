package ro.exceptions;

public class InvalidEdgeCountException extends Exception {
    public InvalidEdgeCountException(int nodeCount) {
        super(String.format("Please introduce a number between %d and %d", 0, nodeCount * (nodeCount - 1) / 2));
    }
}
