package com.example.KU_2024_hackathon.security;

import com.example.KU_2024_hackathon.dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 로그인 실패 로직
@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException
    {
        String errorCode;
        String errorMessage;

        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errorCode = "WRONG";
            errorMessage = "아이디 또는 비밀번호가 틀렸습니다.";
        } else if (exception instanceof DisabledException) {
            errorCode = "DEACTIVATE";
            errorMessage = "해당 계정이 비활성화 되었습니다.";
        } else {
            errorCode = "UNEXPECTED";
            errorMessage = exception.getMessage();
        }

        ErrorDto dto = ErrorDto.builder()
                .code(errorCode)
                .message(errorMessage)
                .data(null)
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(dto));
        response.getWriter().flush();
    }
}
