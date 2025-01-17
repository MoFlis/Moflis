package com.project.moflis.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileReqeust {
    private Integer userId;
    private String intro;
}
