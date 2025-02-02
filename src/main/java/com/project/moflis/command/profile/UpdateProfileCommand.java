package com.project.moflis.command.profile;

import lombok.Getter;

@Getter
public class UpdateProfileCommand {
    private final Integer userId;
    private final String intro;

    public UpdateProfileCommand(Integer userId, String intro) {
        this.userId = userId;
        this.intro = intro;
    }
}
