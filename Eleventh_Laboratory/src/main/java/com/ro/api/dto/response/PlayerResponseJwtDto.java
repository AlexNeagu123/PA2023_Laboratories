package com.ro.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerResponseJwtDto {
    private Long id;
    private String username;
    private String jwt;
}
