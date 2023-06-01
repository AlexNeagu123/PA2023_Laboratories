package com.ro.api.service;

import com.ro.api.dto.request.GameRequestDto;
import com.ro.api.dto.response.GameResponseDto;
import com.ro.api.dto.response.PlayerResponseDto;
import com.ro.api.entity.Game;
import com.ro.api.exceptions.InvalidGameDataException;
import com.ro.api.exceptions.InvalidGamePlayersException;
import com.ro.api.exceptions.InvalidMovesPatternException;
import com.ro.api.exceptions.PlayerNotFoundException;
import com.ro.api.mappers.GameMapper;
import com.ro.api.repository.GameRepository;
import com.ro.api.vsp.VertexSeparator;
import gurobi.GRBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final PlayerService playerService;

    public void addGame(GameRequestDto gameDto) {
        checkGameValidity(gameDto);
        gameRepository.save(gameMapper.mapToGame(gameDto));
    }

    public List<GameResponseDto> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return games.stream().map(gameMapper::mapToGameResponse).toList();
    }

    public List<PlayerResponseDto> computeVertexSeparator() {
        List<GameResponseDto> games = getAllGames();
        List<PlayerResponseDto> players = playerService.getAllPlayers();
        VertexSeparator vertexSeparator = new VertexSeparator(games, players);
        return vertexSeparator.computeVertexSeparator();
    }

    private void checkGameValidity(GameRequestDto gameDto) {
        if (!isValidPlayerString(gameDto.getWinner())) {
            throw new InvalidGameDataException();
        }

        Long firstPlayerId = gameDto.getFirstPlayerId();
        if (!playerExists(firstPlayerId)) {
            throw new PlayerNotFoundException(firstPlayerId);
        }

        Long secondPlayerId = gameDto.getSecondPlayerId();
        if (!playerExists(secondPlayerId)) {
            throw new PlayerNotFoundException(secondPlayerId);
        }

        if (Objects.equals(firstPlayerId, secondPlayerId)) {
            throw new InvalidGamePlayersException();
        }

        if (!isValidMovesPattern(gameDto.getMoves())) {
            throw new InvalidMovesPatternException();
        }
    }

    private boolean playerExists(Long playerId) {
        return playerService.playerWithIdExists(playerId);
    }

    private boolean isValidPlayerString(String playerString) {
        return (playerString.equals("p1") || playerString.equals("p2"));
    }

    private boolean isValidMovesPattern(String movesPattern) {
        String[] movesArr = movesPattern.split("\s+");
        if (movesArr.length % 3 != 0) {
            return false;
        }
        for (int tokenInd = 0; tokenInd < movesArr.length; tokenInd++) {
            String token = movesArr[tokenInd];
            if (tokenInd % 3 == 2 && !(isValidPlayerString(token))) {
                return false;
            }
            if (tokenInd % 3 == 2) {
                continue;
            }
            try {
                int numToken = Integer.parseInt(token);
                if (numToken < 0 || numToken > 14) {
                    return false;
                }
            } catch (NumberFormatException nfe) {
                return false;
            }
        }
        return true;
    }
}
