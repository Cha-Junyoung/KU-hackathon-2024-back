package com.example.KU_2024_hackathon.security;

import jakarta.servlet.http.HttpSession;
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
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   CustomUserDetailsService customUserDetailsService) throws Exception {
        // 기본 설정
        httpSecurity
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
                        .invalidateHttpSession(true)    // 세션 무효화 설정
                        .clearAuthentication(true)      // 인증 정보 제거
                        .logoutSuccessHandler(customLogoutSuccessHandler())
                        .deleteCookies("remember-me", "JSESSIONID")     // 쿠키 삭제
                )
                .userDetailsService(customUserDetailsService);

        // remember-me 설정
        httpSecurity
                .rememberMe((rememberConfig) -> rememberConfig
                        .key("Test-Key")
                        .tokenValiditySeconds(60 * 60 * 24 * 30)        // 30일
                        .rememberMeParameter("remember-me")
                        .userDetailsService(customUserDetailsService)
                );

        // 세션 설정
        httpSecurity
                .sessionManagement((session) -> session
                        .sessionFixation().changeSessionId()                            // 로그인 시, 기존 세션 무효화
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)       // 필요시에 세션을 생성
                        .maximumSessions(1)                                             // 1명만 로그인 가능
                        .maxSessionsPreventsLogin(false)                                // 다른 기기 로그인 시 기존 사용자 세션 만료
                        .sessionRegistry(sessionRegistry())                             // 동시에 로그인한 세션들 추적
                        .expiredSessionStrategy(customSessionExpiredStrategy())         // 만료된 세션으로 요청 시, 처리
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

    // CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://c969-163-152-3-141.ngrok-free.app")); // 허용할 출처 설정
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용 (필요 시)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
