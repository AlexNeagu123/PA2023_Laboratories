package com.ro.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameRequestDto {
    private Long firstPlayerId;
    private Long secondPlayerId;
    private String moves;

    // 0 - first player
    // 1 - second player
    private String winner;
}
