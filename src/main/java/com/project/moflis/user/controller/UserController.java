package com.project.moflis.user.controller;

import com.project.moflis.user.dto.request.FindIdRequest;
import com.project.moflis.user.dto.request.JoinUserRequest;
import com.project.moflis.user.dto.request.LoginUserRequest;
import com.project.moflis.user.dto.response.FindIdResponse;
import com.project.moflis.user.dto.response.JoinUserResponse;
import com.project.moflis.user.dto.response.LoginUserResponse;
import com.project.moflis.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<JoinUserResponse> joinUser(JoinUserRequest request) {
        JoinUserResponse response = userService.joinUser(request.toCommand());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(LoginUserRequest request, HttpServletResponse httpServletResponse) {
        LoginUserResponse response = userService.loginUser(request.toCommand());
        httpServletResponse.setHeader("Authorization", "Bearer " + response.getAccessToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/find-id")
    public ResponseEntity<FindIdResponse> findUserId(FindIdRequest request) {
        FindIdResponse email = userService.findUserEmail(request.getName(), request.getPhone());
        return ResponseEntity.ok(email);
    }


}
