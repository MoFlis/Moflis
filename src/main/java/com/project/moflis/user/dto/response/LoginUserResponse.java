package com.project.moflis.user.dto.response;

import com.project.moflis.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserResponse {
    private Long id;
    private String name;
    private String email;
    private String accessToken;
    private String refreshToken;

    public static LoginUserResponse from(User user, String accessToken, String refreshToken) {
        return new LoginUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                accessToken,
                refreshToken
        );
    }
}