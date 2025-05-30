package com.project.moflis.user.entity;

import com.project.moflis.user.command.JoinUserCommand;
import com.project.moflis.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 반드시 존재해야 함

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime joinDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus;

    @Column
    private String grade;

    public User(long userId) {
        this.id = userId;
    }

    public static User toEntity(JoinUserCommand command, String encodedPassword) {
        return new User(
                null, // ID는 자동 생성
                command.getName(),
                command.getEmail(),
                encodedPassword,
                command.getPhone(),
                command.getBirth(),
                command.getAddress(),
                command.getNickname(),
                command.isGender(),
                command.getKakao(),
                LocalDateTime.now(),
                UserStatus.ACTIVE,
                command.getGrade()
        );
    }


}
