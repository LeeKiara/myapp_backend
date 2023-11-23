package com.lhm.myapp.configuration;

import com.lhm.myapp.auth.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // 서버쪽에서 접근 가능한 origin 설정 => 로컬 호스트 origin 허용
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:5500",
                    "http://127.0.0.1:5500",
                        "https://dmel5zuvyohd2.cloudfront.net",
                        "https://dmel5zuvyohd2.cloudfront.net:8080",
                        "http://ec2-52-78-61-30.ap-northeast-2.compute.amazonaws.com",
                        "http://ec2-52-78-61-30.ap-northeast-2.compute.amazonaws.com:8080")
                .allowedMethods("*");

        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
    }
}
