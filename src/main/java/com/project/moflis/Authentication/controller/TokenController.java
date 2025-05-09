package com.project.moflis.Authentication.controller;

import com.project.moflis.Authentication.dto.request.RefreshTokenRequest;
import com.project.moflis.Authentication.dto.response.TokenResponse;
import com.project.moflis.Authentication.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        TokenResponse response = tokenService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

}
