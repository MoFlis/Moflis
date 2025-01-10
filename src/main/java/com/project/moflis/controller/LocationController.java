package com.project.moflis.controller;

import com.project.moflis.dto.LocationDTO;
import com.project.moflis.dto.UserDTO;
import com.project.moflis.service.LocationService;
import com.project.moflis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/{userId}")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @GetMapping("/location")
    public LocationDTO getLocation(@PathVariable("userId") Integer userId) {
        UserDTO userAddress = userService.getUserAddress(userId);
        System.out.println(userAddress.getAddress());
        Map<String, Double> result = locationService.getCoordinates(userAddress.getAddress());
        LocationDTO location = locationService.saveLocation(result, userId);
        return location;
    }

    @PostMapping("/location-verify")
    public boolean locationVerify(@PathVariable("userId") Integer userId, LocationDTO location) {
        boolean isVerify = locationService.locationVerify(userId, location);
        return isVerify;
    }

}
