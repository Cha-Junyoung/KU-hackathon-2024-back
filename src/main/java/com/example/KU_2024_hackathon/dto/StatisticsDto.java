package com.example.KU_2024_hackathon.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class StatisticsDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class GetColorsRequest {
        @NotBlank(message = "[year] cannot be blank.")
        @Schema(description = "연도")
        private String year;

        @NotBlank(message = "[month] cannot be blank.")
        @Schema(description = "월")
        private String month;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class GetColorsResponse {
        @ArraySchema(arraySchema = @Schema(description = "특정 월의 색깔 리스트", example = "[\"red\", \"blue\", \"green\"]"))
        private String[] colors;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class GetStatisticsRequest {
        @NotBlank(message = "[year] cannot be blank.")
        @Schema(description = "연도")
        private String year;

        @NotBlank(message = "[month] cannot be blank.")
        @Schema(description = "월")
        private String month;

        @NotBlank(message = "[day] cannot be blank.")
        @Schema(description = "일")
        private String day;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class GetStatisticsResponse {
        @Schema(description = "이미지 링크")
        private String imageUrl;

        @Schema(description = "텍스트")
        private String text;

    }


}
