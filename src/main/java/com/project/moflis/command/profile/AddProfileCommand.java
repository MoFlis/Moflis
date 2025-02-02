package com.project.moflis.command.profile;

import lombok.Getter;

@Getter
public class AddProfileCommand {
    private final Integer userId;
    private final String intro;

    public AddProfileCommand(Integer userId, String intro) {
        this.userId = userId;
        this.intro = intro;
    }
}
