package com.ro.server.apirequests;

import com.ro.api.dto.request.GameRequestDto;
import com.ro.api.dto.response.GameResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GameRequestService {
    private static final String URI = "http://localhost:8081/api/v1/games";

    @GetMapping("/call_games")
    public void addGame(GameRequestDto gameDto) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GameRequestDto> requestEntity = new HttpEntity<>(gameDto, headers);
        restTemplate.exchange(URI, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<GameRequestDto>() {
        });
    }

    @GetMapping("/call_games")
    public List<GameResponseDto> getAllGames() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<GameResponseDto>> response = restTemplate.exchange(
                URI, HttpMethod.GET, null, new ParameterizedTypeReference<List<GameResponseDto>>() {}
        );
        return response.getBody();
    }
}
