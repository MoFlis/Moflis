package com.project.moflis.user.application;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.moflis.global.security.jwt.JwtProvider;
import com.project.moflis.global.security.jwt.TokenClaims;
import com.project.moflis.global.security.jwt.TokenClaimsFactory;
import com.project.moflis.token.dto.response.TokenResponse;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserTokenApplicationService {

    private final JwtProvider jwtProvider;
    private final UserService userService;

    public UserTokenApplicationService(JwtProvider jwtProvider,
                                       UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    public TokenResponse accessToken(String accessToken) {
        if (!jwtProvider.verify(accessToken)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        LocalDateTime expiresAt = jwtProvider.getAccessTokenExpiry(accessToken);
        boolean isExpired = expiresAt.isBefore(LocalDateTime.now());
        if (!isExpired) {
            throw new RuntimeException("Access token이 아직 만료되지 않았습니다.");
        }

        DecodedJWT decodedJWT = jwtProvider.decode(accessToken);
        TokenClaims claims = TokenClaimsFactory.from(decodedJWT);
        Long userId = claims.getUserId();

        User user = userService.getByUserId(userId);

        String newAccessToken = jwtProvider.getAccessToken(
                TokenClaims.builder()
                        .userId(user.getId())
                        .name(user.getName())
                        .grade(user.getGrade())
                        .email(user.getEmail())
                        .build()
        );

        return new TokenResponse(newAccessToken);
    }
}
