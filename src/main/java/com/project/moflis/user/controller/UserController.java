package com.project.moflis.user.controller;

import com.project.moflis.user.dto.request.JoinUserRequest;
import com.project.moflis.user.dto.response.JoinUserResponse;
import com.project.moflis.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<JoinUserResponse> joinUser(JoinUserRequest request) {
        System.out.println(request);
        JoinUserResponse response = userService.joinUser(request.toCommand());
        return ResponseEntity.ok(response);
    }
}
