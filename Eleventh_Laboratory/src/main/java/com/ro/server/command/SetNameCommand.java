package com.ro.server.command;

import com.ro.api.dto.request.PlayerRequestDto;
import com.ro.api.dto.response.PlayerResponseDto;
import com.ro.server.apirequests.PlayerRequestService;
import com.ro.server.game.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class SetNameCommand implements Command {
    private final Player player;
    private final String name;

    @Override
    public void execute() {
        sendPlayerDataToApi();
        player.setName(name);
        player.getOutputStream().println("Your name have been successfully set to '" + name + "'");
    }

    private void sendPlayerDataToApi() {
        PlayerRequestService playerRequestService = new PlayerRequestService();
        PlayerRequestDto playerRequestDto = new PlayerRequestDto(this.name);
        if (player.getId() != null) {
            log.info("Updating the name of the player with id " + player.getId() + " to " + this.name);
            playerRequestService.updatePlayer(player.getId(), playerRequestDto);
        } else {
            log.info("Adding a new player with name " + this.name + " to the database");
            PlayerResponseDto responseDto = playerRequestService.addPlayer(playerRequestDto);
            player.setId(responseDto.getId());
        }
    }
}
