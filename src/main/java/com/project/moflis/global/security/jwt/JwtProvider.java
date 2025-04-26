package com.project.moflis.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKeyCode;

    @Value("${jwt.access-token-expire-seconds}")
    private int accessTokenExpireSeconds;

    @Value("${jwt.refresh-token-expire-seconds}")
    private int refreshTokenExpireSeconds;

    private Algorithm algorithm;

    private Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(secretKeyCode);
        }
        return algorithm;
    }

    private String generateToken(Map<String, Object> claims, int seconds) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + 1000L * seconds);

        JWTCreator.Builder builder = JWT.create()
                .withSubject("user")
                .withIssuedAt(now)
                .withExpiresAt(expiresAt);

        // Claims 추가
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue().toString());
        }

        return builder.sign(getAlgorithm());
    }

    public String getAccessToken(Map<String, Object> claims) {
        return generateToken(claims, accessTokenExpireSeconds); // 1시간
    }

    public String getRefreshToken(Map<String, Object> claims) {
        return generateToken(claims, refreshTokenExpireSeconds); // 100일
    }

    public boolean verify(String token) {
        try {
            JWT.require(getAlgorithm())
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Object> getClaims(String token) {
        DecodedJWT decodedJWT = JWT.require(getAlgorithm())
                .build()
                .verify(token);

        return decodedJWT.getClaims().entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().as(Object.class) // Object로 반환
                        )
                );
    }
}