package com.example.KU_2024_hackathon.configuration;

// CORS 설정

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CORS 설정

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedOrigins(
                        "https://localhost:4000",
                        "http://localhost:4000",
                        "https://localhost:3000",
                        "http://localhost:3000",
                        "http://localhost",
                        "https://localhost",
                        "https://web-ku-hackathon-2024-front-m32mykqd6aabb332.sel4.cloudtype.app",
                        "https://c969-163-152-3-141.ngrok-free.app"
                )   // CORS 허용 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // CORS 허용 메서드
    }

}
