package ro.exception;

public class InvalidSystemPath extends Exception {
    public InvalidSystemPath(String path) {
        super(String.format("The %s path has not been found on your system", path));
    }
}
