package com.example.KU_2024_hackathon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class ShareDto
{
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Request
    {
        @NotBlank(message = "[year] cannot be blank.")
        @Schema(description = "year")
        private String year;

        @NotBlank(message = "[month] cannot be blank.")
        @Schema(description = "month")
        private String month;

        @NotBlank(message = "[day] cannot be blank.")
        @Schema(description = "day")
        private String day;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Response
    {
        @NotBlank(message = "[statistics_key] cannot be blank.")
        @Schema(description = "statistics_key")
        private Long statistics_key;

    }
}
