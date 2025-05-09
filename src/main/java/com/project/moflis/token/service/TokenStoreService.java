package com.project.moflis.token.service;

import com.project.moflis.global.security.jwt.JwtProvider;
import com.project.moflis.global.security.jwt.TokenClaims;
import com.project.moflis.token.dto.response.TokenResponse;
import com.project.moflis.token.entity.TokenStore;
import com.project.moflis.token.repository.TokenStoreRepository;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenStoreService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final TokenStoreRepository tokenStoreRepository;

    public TokenStoreService(UserService userService, JwtProvider jwtProvider, TokenStoreRepository tokenStoreRepository) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.tokenStoreRepository = tokenStoreRepository;
    }

    public TokenResponse generateAndStoreTokens(User user) {
        TokenClaims claims = TokenClaims.builder()
                .userId(user.getId())
                .name(user.getName())
                .grade(user.getGrade())
                .build();

        String accessToken = jwtProvider.getAccessToken(claims);
        String refreshToken = jwtProvider.getRefreshToken(claims);
        LocalDateTime expiresAt = jwtProvider.getRefreshTokenExpiry(refreshToken);

        // 기존 토큰 삭제
        tokenStoreRepository.deleteByUserId(user.getId());

        TokenStore tokenStore = TokenStore.builder()
                .userId(user.getId())
                .refreshToken(refreshToken)
                .expiresAt(expiresAt)
                .build();
        tokenStoreRepository.save(tokenStore);

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(String refreshToken) {
        if (!jwtProvider.verify(refreshToken)) {
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }

        TokenClaims claims = jwtProvider.getClaims(refreshToken);
        Long userId = claims.getUserId();

        TokenStore tokenStore = tokenStoreRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("저장된 리프레시 토큰 없음"));

        if (tokenStore.isBlacklisted() || !tokenStore.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("리프레시 토큰 불일치 ");
        }

        User user = userService.getByUserId(userId);
        return generateAndStoreTokens(user);
    }

}
