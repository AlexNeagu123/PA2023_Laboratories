package com.ro.api.mappers;

import com.ro.api.dto.response.PlayerResponseDto;
import com.ro.api.entity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerMapper {
    public PlayerResponseDto mapToPlayerResponse(Player player) {
        return new PlayerResponseDto(player.getId(), player.getUsername());
    }
}
