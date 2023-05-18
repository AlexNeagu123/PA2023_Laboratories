package ro.exceptions;

public class DeleteException extends Exception {
    public DeleteException(String msg, String tableName) {
        super(String.format("Error when deleting from table %s: %s", tableName, msg));
    }
}
