package com.project.moflis.user.dto.response;

import com.project.moflis.user.entity.User;
import lombok.Getter;

@Getter
public class LoginUserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String accessToken;

    public LoginUserResponse(Long id, String name, String email, String accessToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.accessToken = accessToken;
    }

    public static LoginUserResponse from(User user, String accessToken) {
        return new LoginUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                accessToken
        );
    }
}