package com.project.moflis.token.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenRequest {
    private String refreshToken;
}
