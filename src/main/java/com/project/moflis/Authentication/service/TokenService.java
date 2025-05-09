package com.project.moflis.Authentication.service;

import com.project.moflis.Authentication.dto.response.TokenResponse;
import com.project.moflis.global.security.jwt.JwtProvider;
import com.project.moflis.global.security.jwt.TokenClaims;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public TokenService(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    public TokenResponse refreshToken(String refreshToken) {

        if (!jwtProvider.verify(refreshToken)) {
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }

        TokenClaims claims = jwtProvider.getClaims(refreshToken);
        User user = userService.getByUserId(claims.getUserId());

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new RuntimeException("refreshToken이 맞지 않습니다.");
        }

        TokenClaims newClaims = TokenClaims.builder()
                .userId(user.getId())
                .name(user.getName())
                .grade(user.getGrade())
                .build();
        String newAccessToken = jwtProvider.getAccessToken(newClaims);
        String newRefreshToken = jwtProvider.getRefreshToken(newClaims);

        user.updateRefreshToken(newRefreshToken);
        userService.updateRefreshToken(user);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
