package com.ro.server.apirequests;

import com.ro.api.dto.request.PlayerRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerRequestTest {
    PlayerRequestService playerRequestService = new PlayerRequestService();

    @Test
    public void whenGetAllPlayersCalled_itDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(playerRequestService.getAllPlayers());
        });
    }

    @Test
    public void whenUpdatePlayerIsCalled_itDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            playerRequestService.updatePlayer(1L, new PlayerRequestDto("testChange"));
        });
    }

    @Test
    public void whenDeletePlayerIsCalled_itDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            playerRequestService.deletePlayer(1L);
        });
    }

    @Test
    public void whenAddPlayerIsCalled_itDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(playerRequestService.addPlayer(new PlayerRequestDto("alexNeagu")));
        });
    }
}