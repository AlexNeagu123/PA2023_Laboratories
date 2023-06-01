package com.ro.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameResponseDto {
    private Long id;

    private PlayerResponseDto firstPlayer;

    private PlayerResponseDto secondPlayer;

    private String moves;

    private String winner;
}
