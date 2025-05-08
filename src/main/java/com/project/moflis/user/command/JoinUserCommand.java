package com.project.moflis.user.command;

import com.project.moflis.user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class JoinUserCommand {

    private Long id;
    private String name;
    private String email;
    private String password;
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
