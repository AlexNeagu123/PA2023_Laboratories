package ro.server.communication;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.server.command.Command;
import ro.server.command.StopCommand;
import ro.server.exception.*;
import ro.server.game.Player;

import java.io.IOException;

/**
 * The <tt>Shell</tt> class interacts with the client using the input / output pipes stored in the {@link Player} object corresponding to
 * the client.
 * <p>
 * Lines of text are repeatedly read, mapped to a {@link Command} object, and the executed
 */

@RequiredArgsConstructor
@Log4j2
public class Shell {
    private final Player player;
    private final CommandParser commandParser;
    private boolean running = true;

    public void execute() {
        while (this.running) {
            try {
                String commandText = player.getInputStream().readLine();
                Command command = commandParser.parseCommand(commandText);
                command.execute();
                if (command instanceof StopCommand) {
                    this.running = false;
                }
            } catch (GameException e) {
                player.getOutputStream().println(e.getMessage());
            } catch (IOException e) {
                log.error("Error when reading from client: " + e.getMessage());
                this.running = false;
            }
        }
    }
}
