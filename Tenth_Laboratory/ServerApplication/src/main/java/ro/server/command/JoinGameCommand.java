package ro.server.command;

import lombok.RequiredArgsConstructor;
import ro.server.exception.NoAvailableGamesException;
import ro.server.game.Game;
import ro.server.game.GameManager;
import ro.server.game.Player;

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
