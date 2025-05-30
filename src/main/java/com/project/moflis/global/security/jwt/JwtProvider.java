package com.project.moflis.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {

     private final Algorithm algorithm;

    @Value("${jwt.rawSecretKey}")
    private String rawSecretKey;

    @Value("${jwt.access-token-expire-seconds}")
    private int accessTokenExpireSeconds;

    @Value("${jwt.refresh-token-expire-seconds}")
    private int refreshTokenExpireSeconds;

    public JwtProvider(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    private String generateToken(TokenClaims claims, int seconds) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + 1000L * seconds);

        return JWT.create()
                .withSubject(String.valueOf(claims.getUserId()))
                .withIssuer("moflis-api")
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withClaim("userId", claims.getUserId())
                .withClaim("grade", claims.getGrade())
                .sign(algorithm);
    }

    public String getAccessToken(TokenClaims claims) {
        return generateToken(claims, accessTokenExpireSeconds); // 1시간
    }

    public String getRefreshToken(TokenClaims claims) {
        return generateToken(claims, refreshTokenExpireSeconds); // 100일
    }

    public boolean verify(String token) {
        try {
            JWT.require(algorithm)
                    .withIssuer("moflis-api")
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public TokenClaims getClaims(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("moflis-api")
                .build()
                .verify(token);

        Long userId = decodedJWT.getClaim("userId").asLong();
        String name = decodedJWT.getClaim("name").asString();
        String grade = decodedJWT.getClaim("grade").asString();
        String email = decodedJWT.getClaim("email").asString();

        return TokenClaims.builder()
                .userId(userId)
                .name(name)
                .grade(grade)
                .email(email)
                .build();
    }

    public LocalDateTime getRefreshTokenExpiry(String refreshToken) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("moflis-api")
                .build()
                .verify(refreshToken);

        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}