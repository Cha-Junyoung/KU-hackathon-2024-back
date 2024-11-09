package com.example.KU_2024_hackathon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class ShareDto
{
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class ShareRequest
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
    public static class ShareResponse
    {
        @NotBlank(message = "[statistics_key] cannot be blank.")
        @Schema(description = "statistics_key")
        private Long statistics_key;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class View
    {
        @NotBlank(message = "[created_at] cannot be blank.")
        @Schema(description = "created_at")
        private LocalDateTime created_at;

        @NotBlank(message = "[image] cannot be blank.")
        @Schema(description = "image")
        private String image;

        @NotBlank(message = "[text] cannot be blank.")
        @Schema(description = "text")
        private String text;

        @NotBlank(message = "[emotion] cannot be blank.")
        @Schema(description = "emotion")
        private String emotion;

    }
}
