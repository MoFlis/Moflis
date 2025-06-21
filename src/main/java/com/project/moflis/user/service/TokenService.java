package com.project.moflis.user.service;

import com.project.moflis.global.security.jwt.JwtProvider;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final JwtProvider jwtProvider;

    public TokenService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public Long parseUserId(String refreshToken) {
        if (!jwtProvider.verify(refreshToken)) {
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }

        return jwtProvider.decode(refreshToken)
                .getClaim("userId").asLong();
    }


}
