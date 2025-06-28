package com.project.moflis.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindIdRequest {
    private String name;
    private String phone;
}
