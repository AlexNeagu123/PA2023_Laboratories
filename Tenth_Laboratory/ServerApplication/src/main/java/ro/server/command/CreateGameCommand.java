package ro.server.command;

import lombok.RequiredArgsConstructor;
import ro.server.exception.RoomCreationErrorException;
import ro.server.game.Game;
import ro.server.game.GameManager;
import ro.server.game.Player;

@RequiredArgsConstructor
public class CreateGameCommand implements Command {
    private final Player player;

    @Override
    public void execute() throws RoomCreationErrorException {
        Game game = new Game();
        game.addPlayer(player);
        GameManager.getInstance().addGame(game);
        player.getOutputStream().println("Your game room has been created. Currently waiting for other players to join.");
        try {
            while (!game.isAvailable()) {
                Thread.sleep(5000);
                player.getOutputStream().println("Still waiting for a player..");
            }
            player.getOutputStream().println("Hooray! A player named '" + game.getGuestPlayer().getName() + "' entered your room. " +
                    "The game will start shortly.");

            player.setReady(true);
            while (!game.canStart()) {
                Thread.sleep(0);
            }
            game.start();
        } catch (InterruptedException ex) {
            throw new RoomCreationErrorException();
        }
    }
}
