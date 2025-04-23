package com.project.moflis.location.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationCommand {

    private final Long userId;
    private final double latitude;
    private final double longitude;

}
