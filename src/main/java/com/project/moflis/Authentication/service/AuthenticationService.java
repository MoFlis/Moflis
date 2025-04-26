package com.project.moflis.Authentication.service;

import com.project.moflis.Authentication.dto.response.TokenResponse;
import com.project.moflis.global.security.jwt.JwtProvider;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthenticationService(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    public TokenResponse refreshToken(String refreshToken) {

        if (!jwtProvider.verify(refreshToken)) {
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }
        Long userId = (Long) jwtProvider.getClaims(refreshToken).get("userId");
        User user = userService.getUserId(userId);
        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new RuntimeException("refreshToken이 맞지 않습니다.");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("name", user.getName());
        claims.put("grade", user.getGrade());

        String newAccessToken = jwtProvider.getAccessToken(claims);
        String newRefreshToken = jwtProvider.getRefreshToken(claims);
        user.updateRefreshToken(newRefreshToken);
        userService.updateRefreshToken(user);
        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
