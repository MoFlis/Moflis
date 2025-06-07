package com.project.moflis.token.service;

import com.project.moflis.global.security.jwt.JwtProvider;
import com.project.moflis.global.security.jwt.TokenClaims;
import com.project.moflis.token.dto.response.TokenResponse;
import com.project.moflis.token.entity.RefreshToken;
import com.project.moflis.token.repository.RefreshTokenRepository;
import com.project.moflis.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RefreshTokenService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(JwtProvider jwtProvider, RefreshTokenRepository refreshTokenRepository) {
        this.jwtProvider = jwtProvider;
        this.refreshTokenRepository = refreshTokenRepository;
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

        RefreshToken tokenStore = RefreshToken.builder()
                .userId(user.getId())
                .refreshToken(refreshToken)
                .expiresAt(expiresAt)
                .build();
        refreshTokenRepository.save(tokenStore);

        return new TokenResponse(accessToken);
    }

    @Transactional
    public void deleteRefreshToken(Long userId) {
        refreshTokenRepository.deleteById(userId);
    }

    public RefreshToken getByUserId(Long userId) {
        RefreshToken token = refreshTokenRepository.findByUserId(userId);
        if (token == null) {
            throw new RuntimeException("저장된 리프레시 토큰 없음");
        }
        return token;
    }
}
