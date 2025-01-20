package com.project.moflis.command.location;

import lombok.Getter;

@Getter
public class LocationCommand {

    private final Integer userId;
    private final double latitude;
    private final double longitude;

    public LocationCommand(Integer userId, double latitude, double longitude) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
