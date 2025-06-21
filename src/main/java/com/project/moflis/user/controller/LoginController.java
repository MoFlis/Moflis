package com.project.moflis.user.controller;

import com.project.moflis.user.dto.request.LoginUserRequest;
import com.project.moflis.user.dto.request.RefreshTokenRequest;
import com.project.moflis.user.dto.response.LoginUserResponse;
import com.project.moflis.user.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/users")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(LoginUserRequest request, HttpServletResponse httpServletResponse) {
        LoginUserResponse response = loginService.loginUser(request.toCommand());
        httpServletResponse.setHeader("Authorization", "Bearer " + response.getAccessToken());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(RefreshTokenRequest request) {
        loginService.logout(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

}
