package com.project.moflis.command;

import com.project.moflis.profile.command.AddProfileCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileCommandTest {

    @Test
    void ProfileCommand() {
        Long userId = 1;
        String intro = "이것은 테스트";

        AddProfileCommand command = new AddProfileCommand(userId, intro);
        assertEquals(userId, command.getUserId());
        assertEquals(intro, command.getIntro());

    }

}