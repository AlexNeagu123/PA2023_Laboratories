package ro.server.command;

import lombok.RequiredArgsConstructor;
import ro.server.exception.CellOccupiedException;
import ro.server.exception.InvalidTurnException;
import ro.server.game.Player;

@RequiredArgsConstructor
public class SubmitMoveCommand implements Command {
    private final Player player;
    private final int row;
    private final int col;

    @Override
    public void execute() throws CellOccupiedException, InvalidTurnException {
        if (!player.getColor().equals(player.getGame().getCurrentTurn())) {
            throw new InvalidTurnException();
        }

        // If the time limit on the clock has passed
        if (!player.getClock().isAlive()) {
            player.getGame().timeExceededFinish();
            return;
        }

        boolean hasWon = player.getGame().makeMove(row, col);
        player.getOutputStream().println("Your token has been placed on cell (" + row + " " + col + ")!");

        // If the current player has won this match
        if (hasWon) {
            player.getGame().normalFinish();
        } else {
            player.getGame().sendRoundInformation();
        }
    }
}
