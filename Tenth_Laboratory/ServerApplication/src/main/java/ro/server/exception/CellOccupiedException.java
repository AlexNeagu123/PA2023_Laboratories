package ro.server.exception;

public class CellOccupiedException extends GameException {
    public CellOccupiedException(int row, int col, char token) {
        super(String.format("Ooops, the cell (%d, %d) is already occupied by token %c", row, col, token));
    }
}
