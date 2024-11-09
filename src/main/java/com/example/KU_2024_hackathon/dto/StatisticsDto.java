package com.example.KU_2024_hackathon.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
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
    public static class GetColorsResponse {
        @ArraySchema(schema = @Schema(implementation = GetStatisticsInfoResponse.class, description = "특정 월의 날짜, 색깔, 감정 정보"))
        private GetStatisticsInfoResponse[] colors;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @Schema(description = "통계 정보")
    public static class GetStatisticsInfoResponse {
        @Schema(description = "날짜와 시간", example = "2024-11-09T21:37:00")
        private LocalDateTime time;

        @Schema(description = "색깔 정보", example = "red")
        private String color;

        @Schema(description = "감정 정보", example = "joy")
        private String emotion;
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
