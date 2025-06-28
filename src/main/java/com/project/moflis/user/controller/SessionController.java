package com.project.moflis.user.controller;

import com.project.moflis.user.dto.request.LoginUserRequest;
import com.project.moflis.user.dto.request.RefreshTokenRequest;
import com.project.moflis.user.dto.response.LoginUserResponse;
import com.project.moflis.user.service.SessionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/users")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(LoginUserRequest request, HttpServletResponse httpServletResponse) {
        LoginUserResponse response = sessionService.loginUser(request.toCommand());
        httpServletResponse.setHeader("Authorization", "Bearer " + response.getAccessToken());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(RefreshTokenRequest request) {
        sessionService.logout(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

}
