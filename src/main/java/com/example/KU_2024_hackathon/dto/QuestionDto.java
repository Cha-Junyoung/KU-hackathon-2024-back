package com.example.KU_2024_hackathon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class QuestionDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Request {
        @NotBlank(message = "[question1] cannot be blank.")
        @Schema(description = "첫번째 질문")
        private String question1;

        @NotBlank(message = "[question2] cannot be blank.")
        @Schema(description = "두번째 질문")
        private String question2;

        @NotBlank(message = "[question3] cannot be blank.")
        @Schema(description = "세번째 질문")
        private String question3;

        @NotBlank(message = "[answer1] cannot be blank.")
        @Schema(description = "첫번째 질문에 대한 답변")
        private String answer1;

        @NotBlank(message = "[answer2] cannot be blank.")
        @Schema(description = "두번째 질문에 대한 답변")
        private String answer2;

        @NotBlank(message = "[answer3] cannot be blank.")
        @Schema(description = "세번째 질문에 대한 답변")
        private String answer3;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Response {
        @NotBlank(message = "[image_url] cannot be blank.")
        @Schema(description = "S3에 저장된 작품의 이미지 링크")
        private String imageUrl;


        @NotBlank(message = "[text] cannot be blank.")
        @Schema(description = "답변에 대해 생성된 답변")
        private String text;

        @NotBlank(message = "[emotion] cannot be blank.")
        @Schema(description = "답변에 대해 생성된 감정")
        private String emotion;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Questions {
        @NotBlank(message = "[question1] cannot be blank.")
        @Schema(description = "첫번째 질문")
        private String question1;

        @NotBlank(message = "[question2] cannot be blank.")
        @Schema(description = "두번째 질문")
        private String question2;

        @NotBlank(message = "[question3] cannot be blank.")
        @Schema(description = "세번째 질문")
        private String question3;
    }
}
