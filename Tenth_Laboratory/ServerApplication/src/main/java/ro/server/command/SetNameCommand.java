package ro.server.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.server.game.Player;

@RequiredArgsConstructor
@Log4j2
public class SetNameCommand implements Command {
    private final Player player;
    private final String name;

    @Override
    public void execute() {
        player.setName(name);
        player.getOutputStream().println("Your name have been successfully set to '" + name + "'");
    }
}
