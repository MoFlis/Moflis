package com.project.moflis.profile.command;

import lombok.Getter;

@Getter
public class AddProfileCommand {
    private final Long userId;
    private final String intro;

    public AddProfileCommand(Long userId, String intro) {
        this.userId = userId;
        this.intro = intro;
    }
}
