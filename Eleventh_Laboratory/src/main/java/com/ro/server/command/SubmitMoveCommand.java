package com.ro.server.command;

import com.ro.server.exception.CellOccupiedException;
import com.ro.server.exception.InvalidTurnException;
import com.ro.server.game.Game;
import com.ro.server.game.Player;
import lombok.RequiredArgsConstructor;

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

        Game game = player.getGame();
        game.addMove(row, col, game.getPlayerString(player));
        // If the time limit on the clock has passed
        if (!player.getClock().isAlive()) {
            player.getGame().timeExceededFinish();
            return;
        }

        boolean hasWon = player.getGame().makeMove(row, col);
        player.getOutputStream().println("Your token has been placed on cell (" + row + " " + col + ")!");

        // If the current player has won this match
        if (hasWon) {
            game.setWinnerToken(game.getPlayerString(player));
            game.normalFinish();
        } else {
            game.sendRoundInformation();
        }
    }
}
