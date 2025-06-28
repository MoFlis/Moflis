package com.project.moflis.user.command;

import com.project.moflis.user.enums.UserStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class LoginUserCommand {
    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private final String phone;
    private final LocalDate birth;
    private final String address;
    private final String nickname;
    private final boolean gender;
    private final int kakao;
    private final LocalDateTime joinDate;
    private final UserStatus userStatus;
    private final String grade;

    public LoginUserCommand(Long id, String name, String email, String password, String phone, LocalDate birth, String address, String nickname, boolean gender, int kakao, LocalDateTime joinDate, UserStatus userStatus, String grade) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birth = birth;
        this.address = address;
        this.nickname = nickname;
        this.gender = gender;
        this.kakao = kakao;
        this.joinDate = joinDate;
        this.userStatus = userStatus;
        this.grade = grade;
    }
}
