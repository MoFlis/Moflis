package com.project.moflis.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.moflis.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 반드시 존재해야 함

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private LocalDate birth;

    @Column
    private String address;

    @Column
    private String nickname;

    @Column
    private boolean gender;

    @Column
    private int kakao;

    @Column
    private LocalDateTime joinDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus;

    @Column
    private String grade;
}
