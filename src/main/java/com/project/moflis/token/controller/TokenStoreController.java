package com.project.moflis.token.controller;

import com.project.moflis.token.dto.request.RefreshTokenRequest;
import com.project.moflis.token.dto.response.TokenResponse;
import com.project.moflis.token.service.TokenStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class TokenStoreController {

    private final TokenStoreService tokenStoreService;

    public TokenStoreController(TokenStoreService tokenStoreService) {
        this.tokenStoreService = tokenStoreService;
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        TokenResponse response = tokenStoreService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

}
