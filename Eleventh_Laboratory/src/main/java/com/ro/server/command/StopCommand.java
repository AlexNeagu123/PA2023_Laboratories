package com.ro.server.command;

import com.ro.server.game.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StopCommand implements Command {
    private final Player player;

    @Override
    public void execute() {
        player.getOutputStream().println("Server Stopped");
    }
}
