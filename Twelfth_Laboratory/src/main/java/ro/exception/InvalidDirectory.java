package ro.exception;

public class InvalidDirectory extends Exception {
    public InvalidDirectory(String dirPath) {
        super(String.format("%s is not a directory", dirPath));
    }
}
