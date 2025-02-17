package com.project.moflis.dto.profile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddProfileRequest {

    @NotNull(message = "User ID는 필수 입력값입니다.")
    @Positive(message = "User ID는 양수여야 합니다.")
    private Integer userId;

    @Size(max = 500, message = "소개글은 최대 500자까지 입력 가능합니다.")
    private String intro;
}
