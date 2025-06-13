package com.project.moflis.emailverification.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class TokenGenerator {

    private static final SecureRandom random = new SecureRandom();
    private static final int TOKEN_LENGTH = 6;

    public String generateToken() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}

