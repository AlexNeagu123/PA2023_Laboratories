package com.ro.api.controller;

import com.ro.api.dto.request.PlayerRequestDto;
import com.ro.api.dto.response.PlayerResponseDto;
import com.ro.api.dto.response.PlayerResponseJwtDto;
import com.ro.api.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping
    @Operation(tags = {"Player"})
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerResponseJwtDto createPlayer(@RequestBody PlayerRequestDto playerDto) {
        return playerService.addPlayer(playerDto);
    }

    @GetMapping
    @Operation(tags = {"Player"})
    public List<PlayerResponseDto> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @PutMapping("/{id}")
    @Operation(tags = {"Player"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePlayer(@RequestBody PlayerRequestDto playerDto, @PathVariable Long id) {
        playerService.updatePlayer(id, playerDto);
    }

    @DeleteMapping("/{id}")
    @Operation(tags = {"Player"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayer(@PathVariable Long id, Authentication authentication) {
        System.out.println(authentication);
        playerService.deletePlayerById(id);
    }
}
