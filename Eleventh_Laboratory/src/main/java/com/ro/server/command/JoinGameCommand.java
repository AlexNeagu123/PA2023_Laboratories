package com.ro.server.command;

import com.ro.server.exception.NoAvailableGamesException;
import com.ro.server.game.Game;
import com.ro.server.game.GameManager;
import com.ro.server.game.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JoinGameCommand implements Command {
    private final Player player;

    @Override
    public void execute() throws NoAvailableGamesException {
        Game game = GameManager.getInstance().getGame();
        game.addPlayer(player);
        player.setGame(game);
        player.getOutputStream().println("Hooray! You have successfully connected to the game room created by player '" +
                game.getHostPlayer().getName() + "'. The game will start shortly.");
        player.setReady(true);
    }
}
