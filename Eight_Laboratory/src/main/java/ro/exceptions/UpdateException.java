package ro.exceptions;

public class UpdateException extends Exception {
    public UpdateException(String msg, String tableName) {
        super(String.format("Error when executing an update operation on table %s: %s", tableName, msg));
    }
}
