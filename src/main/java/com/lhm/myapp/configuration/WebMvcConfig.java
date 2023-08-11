package com.lhm.myapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // 서버쪽에서 접근 가능한 origin 설정 => 로컬 호스트 origin 허용
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:5500",
                    "http://127.0.0.1:5500")
                .allowedMethods("*");

        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
