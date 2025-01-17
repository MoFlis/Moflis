package com.project.moflis.command;

import lombok.Getter;

@Getter
public class ProfileCommand {
    private final Integer userId;
    private final String intro;

    public ProfileCommand(Integer userId, String intro) {
        this.userId = userId;
        this.intro = intro;
    }
}
