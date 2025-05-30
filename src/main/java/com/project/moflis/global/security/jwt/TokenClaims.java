package com.project.moflis.global.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenClaims {
    private Long userId;
    private String name;
    private String grade;
    private String email;
}