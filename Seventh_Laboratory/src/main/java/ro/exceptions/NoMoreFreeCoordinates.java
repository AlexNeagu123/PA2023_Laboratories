package ro.exceptions;

public class NoMoreFreeCoordinates extends Exception {
    public NoMoreFreeCoordinates() {
        super("There are no more free cells available to place a robot!");
    }
}
