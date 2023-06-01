package com.ro.server.apirequests;

import com.ro.api.dto.request.GameRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameRequestServiceTest {
    private final GameRequestService gameRequestService = new GameRequestService();

    @Test
    public void whenGetAllGamesCalled_itDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(gameRequestService.getAllGames());
        });
    }

    @Test
    public void whenAddGameIsCalled_itDoesNotThrow() {
        Assertions.assertDoesNotThrow(() -> {
            gameRequestService.addGame(new GameRequestDto(
                    2L,
                    4L,
                    "0 1 p1 0 2 p2 0 1 p1",
                    "p1"
            ));
        });
    }
}