package com.project.moflis.Authentication.controller;

import com.project.moflis.Authentication.dto.request.RefreshTokenRequest;
import com.project.moflis.Authentication.dto.response.TokenResponse;
import com.project.moflis.Authentication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<TokenResponse> refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        TokenResponse response = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

}
