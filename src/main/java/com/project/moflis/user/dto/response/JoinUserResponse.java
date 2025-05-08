package com.project.moflis.user.dto.response;

import com.project.moflis.user.enums.UserStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class JoinUserResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birth;
    private String address;
    private String nickname;
    private boolean gender;
    private int kakao;
    private LocalDateTime joinDate;
    private UserStatus userStatus;
    private String grade;
}
