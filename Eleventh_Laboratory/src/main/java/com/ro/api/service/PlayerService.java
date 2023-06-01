package com.ro.api.service;

import com.ro.api.dto.request.PlayerRequestDto;
import com.ro.api.dto.response.PlayerResponseDto;
import com.ro.api.dto.response.PlayerResponseJwtDto;
import com.ro.api.entity.Player;
import com.ro.api.exceptions.PlayerNotFoundException;
import com.ro.api.mappers.PlayerMapper;
import com.ro.api.repository.PlayerRepository;
import com.ro.api.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public PlayerResponseJwtDto addPlayer(PlayerRequestDto playerDto) {
        Player addedPlayer = playerRepository.save(new Player(playerDto.getUsername()));
        String jwt = jwtTokenProvider.generateJwtToken(addedPlayer.getId());

        return new PlayerResponseJwtDto(
                addedPlayer.getId(),
                addedPlayer.getUsername(),
                jwt
        );
    }

    public List<PlayerResponseDto> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        return players.stream().map(playerMapper::mapToPlayerResponse).toList();
    }

    public void updatePlayer(Long id, PlayerRequestDto playerDto) {
        if (!playerWithIdExists(id)) {
            throw new PlayerNotFoundException(id);
        }
        Player updatedPlayer = new Player(id, playerDto.getUsername());
        playerRepository.save(updatedPlayer);
    }

    public void deletePlayerById(Long id) {
        if (!playerWithIdExists(id)) {
            throw new PlayerNotFoundException(id);
        }
        playerRepository.deleteById(id);
    }

    boolean playerWithIdExists(Long id) {
        return playerRepository.findById(id).isPresent();
    }

    public Player findPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }
}
