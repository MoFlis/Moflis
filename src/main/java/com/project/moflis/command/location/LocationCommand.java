package com.project.moflis.command.location;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationCommand {

    private final Integer userId;
    private final double latitude;
    private final double longitude;

}
