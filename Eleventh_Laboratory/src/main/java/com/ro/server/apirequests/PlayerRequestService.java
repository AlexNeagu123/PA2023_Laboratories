package com.ro.server.apirequests;

import com.ro.api.dto.request.PlayerRequestDto;
import com.ro.api.dto.response.PlayerResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class PlayerRequestService {
    private static final String URI = "http://localhost:8081/api/v1/players";

    @GetMapping("/call_players")
    public List<PlayerResponseDto> getAllPlayers() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<PlayerResponseDto>> response = restTemplate.exchange(
                URI, HttpMethod.GET, null, new ParameterizedTypeReference<List<PlayerResponseDto>>() {
                }
        );
        return response.getBody();
    }

    @PutMapping("/call_players")
    public void updatePlayer(Long playerId, PlayerRequestDto playerDto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PlayerRequestDto> requestEntity = new HttpEntity<>(playerDto, headers);
        restTemplate.exchange(
                URI + "/" + playerId, HttpMethod.PUT, requestEntity, Void.class, playerId
        );
    }

    @PostMapping("/call_players")
    public PlayerResponseDto addPlayer(PlayerRequestDto playerDto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PlayerRequestDto> requestEntity = new HttpEntity<>(playerDto, headers);
        ResponseEntity<PlayerResponseDto> response = restTemplate.exchange(URI, HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<PlayerResponseDto>() {
                });

        return response.getBody();
    }

    @DeleteMapping("/call_players")
    public void deletePlayer(Long playerId) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(
                URI + "/" + playerId, HttpMethod.DELETE, null, Void.class, playerId
        );
    }
}
