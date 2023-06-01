package com.ro.api.controller;

import com.ro.api.dto.request.GameRequestDto;
import com.ro.api.dto.response.GameResponseDto;
import com.ro.api.dto.response.PlayerResponseDto;
import com.ro.api.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/games")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping
    @Operation(tags = {"Game"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createGame(@RequestBody GameRequestDto gameDto) {
        gameService.addGame(gameDto);
    }

    @GetMapping
    @Operation(tags = {"Game"})
    public List<GameResponseDto> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping(value = "/vsp")
    @Operation(tags = {"Game"})
    @ResponseStatus(HttpStatus.CREATED)
    public List<PlayerResponseDto> computeVertexSeparator() {
        return gameService.computeVertexSeparator();
    }
}
