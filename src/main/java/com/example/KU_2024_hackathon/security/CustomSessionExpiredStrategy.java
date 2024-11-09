package com.example.KU_2024_hackathon.security;

import com.example.KU_2024_hackathon.dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 세션 만료 로직
@Component
public class CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {

        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();

        ErrorDto dto = ErrorDto.builder()
                .code("EXPIRED_SESSION")
                .message("세션이 만료되었습니다.")
                .data(null)
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(dto));
        response.getWriter().flush();
    }
}
