package com.project.moflis.command;

import com.project.moflis.command.profile.AddProfileCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileCommandTest {

    @Test
    void ProfileCommand() {
        Integer userId = 1;
        String intro = "이것은 테스트";

        AddProfileCommand command = new AddProfileCommand(userId, intro);
        assertEquals(userId, command.getUserId());
        assertEquals(intro, command.getIntro());

    }

}