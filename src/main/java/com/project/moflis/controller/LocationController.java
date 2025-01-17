package com.project.moflis.controller;

import com.project.moflis.command.LocationCommand;
import com.project.moflis.dto.location.LocationDTO;
import com.project.moflis.dto.location.VerifyLocationRequest;
import com.project.moflis.dto.user.UserDTO;
import com.project.moflis.service.LocationService;
import com.project.moflis.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/{userId}")
public class LocationController {

    private final LocationService locationService;

    private final UserService userService;

    public LocationController(LocationService locationService, UserService userService) {
        this.locationService = locationService;
        this.userService = userService;
    }

    @PostMapping("/location")
    public ResponseEntity<LocationDTO> saveLocation(@PathVariable("userId") Integer userId) {
        UserDTO userAddress = userService.getUserAddress(userId);
        System.out.println(userAddress.getAddress());
        Map<String, Double> result = locationService.getCoordinates(userAddress.getAddress());
        LocationDTO location = locationService.saveLocation(result, userId);
        return ResponseEntity.ok(location);
    }

    @PostMapping("/location-verify")
    public boolean locationVerify(@PathVariable("userId") Integer userId, @RequestBody VerifyLocationRequest request) {
        request.setUserId(userId);
        LocationCommand command = new LocationCommand(
                request.getUserId(),
                request.getLatitude(),
                request.getLongitude()
        );
        boolean isVerify = locationService.locationVerify(command);
        return isVerify;
    }

}
