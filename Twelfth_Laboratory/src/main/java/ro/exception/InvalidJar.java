package ro.exception;

public class InvalidJar extends Exception {
    public InvalidJar(String jarPath) {
        super(String.format("%s is not a jar file", jarPath));
    }
}
