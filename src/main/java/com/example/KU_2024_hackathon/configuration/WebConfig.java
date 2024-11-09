package com.example.KU_2024_hackathon.configuration;

// CORS 설정

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry  registry) {
        registry
                .addMapping("/**")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedOrigins("https://localhost:4000", "http://localhost:4000","https://localhost:3000", "http://localhost:3000", "https://127.0.0.1:4000",
                        "http://127.0.0.1:4000","https://127.0.0.1:3000",
                        "http://127.0.0.1:3000", "https://academ-frontend.vercel.app", "https://13.124.249.107.nip.io") // added more origin
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Wildcard method not works in some browsers, and HTTPS
    }

}
