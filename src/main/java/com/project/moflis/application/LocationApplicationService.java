package com.project.moflis.application;

import com.project.moflis.command.location.LocationCommand;
import com.project.moflis.dto.location.CoordinatesDTO;
import com.project.moflis.dto.location.LocationResponseDTO;
import com.project.moflis.dto.user.UserDTO;
import com.project.moflis.service.LocationService;
import com.project.moflis.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class LocationApplicationService {
    private final UserService userService;
    private final LocationService locationService;

    public LocationApplicationService(UserService userService, LocationService locationService) {
        this.userService = userService;
        this.locationService = locationService;
    }

    public LocationResponseDTO processAndSaveLocation(Integer userId) {
        UserDTO userAddress = userService.getUserAddress(userId);
        CoordinatesDTO coordinates = locationService.getCoordinates(userAddress.getAddress());
        return locationService.saveLocation(coordinates, userId);
    }

    public boolean verifyLocation(LocationCommand command) {
        return locationService.locationVerify(command);
    }


}
