package com.ro.api.mappers;

import com.ro.api.dto.request.GameRequestDto;
import com.ro.api.dto.response.GameResponseDto;
import com.ro.api.entity.Game;
import com.ro.api.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GameMapper {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public Game mapToGame(GameRequestDto gameDto) {
        return new Game(
                playerService.findPlayerById(gameDto.getFirstPlayerId()),
                playerService.findPlayerById(gameDto.getSecondPlayerId()),
                gameDto.getMoves(),
                gameDto.getWinner()
        );
    }

    public GameResponseDto mapToGameResponse(Game game) {
        return new GameResponseDto(
                game.getId(),
                playerMapper.mapToPlayerResponse(game.getFirstPlayer()),
                playerMapper.mapToPlayerResponse(game.getSecondPlayer()),
                game.getMoves(),
                game.getWinner()
        );
    }
}
