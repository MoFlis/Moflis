package com.project.moflis.command;

import com.project.moflis.location.command.LocationCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationCommandTest {

    @Test
    void testLocationCommand() {
        Long userId = 1;
        double latitude = 37.7749;
        double longitude = -122.4194;
        LocationCommand locationCommand = new LocationCommand(userId, latitude, longitude);

        assertEquals(userId, locationCommand.getUserId());
        assertEquals(latitude, locationCommand.getLatitude());
        assertEquals(longitude, locationCommand.getLongitude());
    }

}