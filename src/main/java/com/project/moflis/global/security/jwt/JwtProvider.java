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

    @Value("${jwt.access-token-expire-seconds}")
    private int accessTokenExpireSeconds;

    @Value("${jwt.refresh-token-expire-seconds}")
    private int refreshTokenExpireSeconds;

    @Value("${jwt.issuer}")
    private String issuer;

    public JwtProvider(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    private String generateToken(TokenClaims claims, int seconds) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + 1000L * seconds);

        return JWT.create()
                .withSubject(String.valueOf(claims.getUserId()))
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withClaim("userId", claims.getUserId())
                .withClaim("grade", claims.getGrade())
                .withClaim("email", claims.getEmail())
                .sign(algorithm);
    }

    public String getAccessToken(TokenClaims claims) {
        return generateToken(claims, accessTokenExpireSeconds);
    }

    public String getRefreshToken(TokenClaims claims) {
        return generateToken(claims, refreshTokenExpireSeconds);
    }

    public boolean verify(String token) {
        try {
            JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DecodedJWT decode(String token) {
        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
    }

    public LocalDateTime getRefreshTokenExpiry(String refreshToken) {
        DecodedJWT decodedJWT = decode(refreshToken);
        return decodedJWT.getExpiresAt()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}