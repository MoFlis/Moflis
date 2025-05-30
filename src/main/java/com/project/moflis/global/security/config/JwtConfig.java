package com.project.moflis.global.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.algorithms.Algorithm;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String rawSecretKey;

    @Bean
    public Algorithm jwtAlgorithm() {
        return Algorithm.HMAC256(rawSecretKey);
    }
}
