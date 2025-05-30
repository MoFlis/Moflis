package com.project.moflis.global.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenClaimsFactory {

    public static TokenClaims from(DecodedJWT decodedJWT) {
        return TokenClaims.builder()
                .userId(decodedJWT.getClaim("userId").asLong())
                .name(decodedJWT.getClaim("name").asString())
                .grade(decodedJWT.getClaim("grade").asString())
                .email(decodedJWT.getClaim("email").asString())
                .build();
    }
}