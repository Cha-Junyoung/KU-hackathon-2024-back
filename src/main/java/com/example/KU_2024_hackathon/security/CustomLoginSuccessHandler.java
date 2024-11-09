package com.example.KU_2024_hackathon.security;


import com.example.KU_2024_hackathon.dto.ProfileDto;
import com.example.KU_2024_hackathon.dto.TokenDto;
import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.exception.CustomErrorCode;
import com.example.KU_2024_hackathon.exception.CustomException;
import com.example.KU_2024_hackathon.repository.ProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 로그인 성공 로직
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler
{
    @Autowired JwtUtil jwtUtil;
    @Autowired ProfileRepository profileRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException
    {
        // 로그인 이메일
        String email = authentication.getName();

        // body에서 remember-me 값 추출
        String rememberMeParam = request.getParameter("remember-me");
        boolean rememberMe = rememberMeParam != null && rememberMeParam.equalsIgnoreCase("true");

        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.LOGIN_FAILURE, email));

        ProfileDto.Profile profileDto = ProfileDto.Profile.builder()
                .id(profile.getId())
                .email(profile.getEmail())
                .password(profile.getPassword())
                .nickname(profile.getNickname())
                .role(profile.getRole())
                .build();

        // token 발행
        TokenDto tokenDto = jwtUtil.returnToken(profileDto, rememberMe);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(tokenDto));
        response.getWriter().flush();
    }
}
