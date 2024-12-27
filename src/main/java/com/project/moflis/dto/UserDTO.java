package com.project.moflis.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id; // 반드시 존재해야 함
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate birth;
    private String address;
    private String nickname;
    private Character gender;
    private int kakao;
    private LocalDateTime joinDate;
    private boolean status;
    private String grade;

}
