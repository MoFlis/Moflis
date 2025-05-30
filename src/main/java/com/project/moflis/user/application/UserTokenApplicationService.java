package com.project.moflis.user.application;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.moflis.global.security.jwt.JwtProvider;
import com.project.moflis.global.security.jwt.TokenClaims;
import com.project.moflis.global.security.jwt.TokenClaimsFactory;
import com.project.moflis.token.dto.response.TokenResponse;
import com.project.moflis.token.entity.RefreshToken;
import com.project.moflis.token.repository.RefreshTokenRepository;
import com.project.moflis.token.service.RefreshTokenService;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserTokenApplicationService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public UserTokenApplicationService(JwtProvider jwtProvider,
                                       RefreshTokenRepository refreshTokenRepository,
                                       UserService userService,
                                       RefreshTokenService refreshTokenService) {
        this.jwtProvider = jwtProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    public TokenResponse refreshToken(String refreshToken) {
        if (!jwtProvider.verify(refreshToken)) {
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }

        DecodedJWT decodedJWT = jwtProvider.decode(refreshToken);
        TokenClaims claims = TokenClaimsFactory.from(decodedJWT);
        Long userId = claims.getUserId();

        RefreshToken tokenStore = refreshTokenRepository.findByUserId(userId);
        if (tokenStore == null) {
            throw new RuntimeException("저장된 리프레시 토큰 없음");
        }

        if (!tokenStore.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("서버에 저장된 리프레시 토큰과 일치하지 않습니다");
        }

        User user = userService.getByUserId(userId);

        boolean generateNewRefreshToken = tokenStore.getExpiresAt().isBefore(LocalDateTime.now().plusDays(3));

        if (generateNewRefreshToken) {
            refreshTokenService.generateAndStoreTokens(user);
        }

        String newAccessToken = jwtProvider.getAccessToken(
                TokenClaims.builder()
                        .userId(user.getId())
                        .name(user.getName())
                        .grade(user.getGrade())
                        .build());

        return new TokenResponse(newAccessToken);
    }
}
