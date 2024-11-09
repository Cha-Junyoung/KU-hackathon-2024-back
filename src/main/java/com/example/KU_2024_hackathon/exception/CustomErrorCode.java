package com.example.KU_2024_hackathon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CustomErrorCode
{
    ALREADY_USED_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없습니다."),
    INVALID_COLOR_CODE(HttpStatus.BAD_REQUEST, "색상 코드는 6자리여야 합니다."),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "질문을 찾을 수 없습니다."),
    LOGIN_FAILURE(HttpStatus.FORBIDDEN, "로그인에 실패하였습니다."),
    STATISTICS_NOT_FOUND(HttpStatus.NOT_FOUND, "통계 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
