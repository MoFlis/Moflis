package com.project.moflis.profile.command;

import lombok.Getter;

@Getter
public class UpdateProfileCommand {
    private final Long userId;
    private final String intro;

    public UpdateProfileCommand(Long userId, String intro) {
        this.userId = userId;
        this.intro = intro;
    }
}
