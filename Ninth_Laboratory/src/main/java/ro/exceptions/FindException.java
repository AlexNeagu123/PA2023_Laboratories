package ro.exceptions;

public class FindException extends Exception {
    public FindException(String type, String msg, String tableName) {
        super(String.format("Error when executing a %s query in table %s: %s", type, tableName, msg));
    }
}
