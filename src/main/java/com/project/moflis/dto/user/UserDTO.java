package com.project.moflis.dto.user;

import com.project.moflis.enums.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO {

    private Integer id; // 반드시 존재해야 함
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
