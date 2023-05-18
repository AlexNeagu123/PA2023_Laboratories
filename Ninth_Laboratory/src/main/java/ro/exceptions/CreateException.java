package ro.exceptions;

public class CreateException extends Exception {
    public CreateException(String msg, String tableName) {
        super(String.format("Error when inserting a row in table %s: %s", tableName, msg));
    }
}
