package com.example.KU_2024_hackathon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class TokenDto
{
    @Schema(description = "access token")
    private String accessToken;

    @Schema(description = "refresh token")
    private String refreshToken;
}
