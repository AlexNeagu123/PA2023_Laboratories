package ro.server.command;

import lombok.RequiredArgsConstructor;
import ro.server.game.Player;

@RequiredArgsConstructor
public class StopCommand implements Command {
    private final Player player;

    @Override
    public void execute() {
        player.getOutputStream().println("Server Stopped");
    }
}
