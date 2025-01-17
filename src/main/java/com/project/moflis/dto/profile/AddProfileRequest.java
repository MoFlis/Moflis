package com.project.moflis.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProfileRequest {
    private Integer userId;
    private String intro;
}
