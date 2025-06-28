package com.project.moflis.user.factory;

import com.project.moflis.user.command.JoinUserCommand;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.enums.UserStatus;

import java.time.LocalDateTime;

public class UserFactory {

    public static User createUser(JoinUserCommand command, String encodedPassword) {
        return new User(
                null,
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
