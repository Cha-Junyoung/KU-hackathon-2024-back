package com.example.KU_2024_hackathon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class ProfileDto
{
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Profile
    {
        @NotNull(message = "[profile_id] cannot be null.")
        @Schema(description = "profile_id")
        private Long profile_id;

        @NotBlank(message = "[id] cannot be blank.")
        @Schema(description = "아이디")
        private String id;

        @NotBlank(message = "[password] cannot be blank.")
        @Schema(description = "비밀번호")
        private String password;

        @NotBlank(message = "[nickname] cannot be blank.")
        @Schema(description = "닉네임")
        private String nickname;

        @NotNull(message = "[created_at] cannot be null.")
        @Schema(description = "생성 날짜")
        private LocalDate created_at;

        @NotBlank(message = "[role] cannot be blank.")
        @Schema(description = "권한")
        private String role;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Join
    {
        @NotBlank(message = "[id] cannot be blank.")
        @Schema(description = "아이디")
        private String id;

        @NotBlank(message = "[password] cannot be blank.")
        @Schema(description = "비밀번호")
        private String password;

        @NotBlank(message = "[nickname] cannot be blank.")
        @Schema(description = "닉네임")
        private String nickname;
    }
}
