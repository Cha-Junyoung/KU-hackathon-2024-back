package com.example.KU_2024_hackathon.security;

import com.example.KU_2024_hackathon.repository.ProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Autowired JwtUtil jwtUtil;
    @Autowired ProfileRepository profileRepository;
    @Autowired ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   CustomUserDetailsService customUserDetailsService) throws Exception {
        // 기본 설정
        httpSecurity
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .httpBasic(HttpBasicConfigurer::disable)    // HTTP 기본 인증 비활성화
                .csrf(CsrfConfigurer::disable);             // CSRF 보호 비활성화

        // 경로별 권한 설정
        httpSecurity
                .authorizeHttpRequests((requests) -> requests
                        // 로그인, 로그아웃
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/logout").hasAnyRole("ADMIN", "USER")
                        // 프로필
                        .requestMatchers("/api/profile/join").permitAll()
                        // 테스트
                        .requestMatchers("/api/test/test-all").permitAll()
                        .requestMatchers("/api/test/test-user").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/test/test-admin").hasRole("ADMIN")
                        // ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // QUESTION
                        .requestMatchers("/api/question/**").hasAnyRole("ADMIN", "USER")
                        // 공유
                        .requestMatchers("/api/share").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/share/view").permitAll()
                        // 기타
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        // 그 외의 요청은 로그인을 수행한 사용자에게만 접근 권한 허용
                        .anyRequest().authenticated()
                );

        // 예외 처리 설정
        httpSecurity
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint())
                        .accessDeniedHandler(customAccessDeniedHandler())
                );

        // UsernamePasswordAuthenticationFilter 앞에 JwtAuthFilter 추가
        httpSecurity
                .addFilterBefore(new JwtAuthFilter(customUserDetailsService, jwtUtil, profileRepository), UsernamePasswordAuthenticationFilter.class);

        // 로그인, 로그아웃 설정
        httpSecurity
                .formLogin((login) -> login
                        .usernameParameter("email")                     // 아이디
                        .passwordParameter("password")                  // 비밀번호
                        .loginProcessingUrl("/api/login")               // 로그인 처리 경로
                        .permitAll()
                        .successHandler(customLoginSuccessHandler())    // 로그인 성공 로직
                        .failureHandler(customLoginFailureHandler())    // 로그인 실패 로직
                )
                .logout((logout) -> logout
                        .logoutUrl("/api/logout")       // 로그아웃 처리 경로
                        .logoutSuccessHandler(customLogoutSuccessHandler())
                )
                .userDetailsService(customUserDetailsService);

        // 세션 생성 및 사용 정지
        httpSecurity
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return httpSecurity.build();
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    public CustomLoginFailureHandler customLoginFailureHandler() {
        return new CustomLoginFailureHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler customLogoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public CustomSessionExpiredStrategy customSessionExpiredStrategy() {
        return new CustomSessionExpiredStrategy();
    }

    // 세션 생성, 만료 이벤트 리스너
    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    // ⭐️ CORS 설정
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("*")); // 허용할 origin
            config.setAllowCredentials(true);
            return config;
        };
    }
}
