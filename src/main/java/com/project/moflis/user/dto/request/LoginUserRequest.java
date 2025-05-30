package com.project.moflis.user.dto.request;

import com.project.moflis.user.command.LoginUserCommand;
import com.project.moflis.user.enums.UserStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class LoginUserRequest {

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

    public LoginUserCommand toCommand() {
        return new LoginUserCommand(
                this.id,
                this.name,
                this.email,
                this.password,
                this.phone,
                this.birth,
                this.address,
                this.nickname,
                this.gender,
                this.kakao,
                this.joinDate,
                this.userStatus,
                this.grade
        );
    }
}
