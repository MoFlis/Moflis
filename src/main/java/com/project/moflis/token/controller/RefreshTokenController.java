package com.project.moflis.token.controller;

import com.project.moflis.token.dto.request.RefreshTokenRequest;
import com.project.moflis.token.dto.response.TokenResponse;
import com.project.moflis.user.application.UserTokenApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class RefreshTokenController {

    private final UserTokenApplicationService userTokenApplicationService;

    public RefreshTokenController(UserTokenApplicationService userTokenApplicationService) {
        this.userTokenApplicationService = userTokenApplicationService;
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        TokenResponse response = userTokenApplicationService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

}
