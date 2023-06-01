package ro.exception;

public class InvalidClass extends Exception {
    public InvalidClass(String className) {
        super(String.format("The class located at path %s was not found on your system", className));
    }
}
