package com.example.KU_2024_hackathon.security;

import com.example.KU_2024_hackathon.entity.Profile;
import com.example.KU_2024_hackathon.exception.CustomErrorCode;
import com.example.KU_2024_hackathon.exception.CustomException;
import com.example.KU_2024_hackathon.repository.ProfileRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter
{
    private final CustomUserDetailsService customUserDetailService;
    private final JwtUtil jwtUtil;
    private final ProfileRepository profileRepository;

    // JWT 토큰 검증 필터
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        // 헤더에 JWT token이 존재하는지 체크
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            String token = authorizationHeader.substring(7);

            // JWT 유효성 검증
            if(jwtUtil.validateToken(token))
            {
                Long id = jwtUtil.getId(token);
                UserDetails userDetails = customUserDetailService.loadUserById(id);

                Profile profile = profileRepository.findById(id)
                        .orElseThrow(() -> new CustomException(CustomErrorCode.EMAIL_NOT_FOUND, null));

                String email = profile.getEmail();

                if(userDetails != null)
                {
                    // 접근 권한 인증 token 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // 현재 요청의 security context에 접근 권한 부여
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        // 다음 필터로 전달
        filterChain.doFilter(request, response);
    }
}
