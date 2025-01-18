package com.project.moflis.controller;

import com.project.moflis.application.LocationApplicationService;
import com.project.moflis.dto.location.LocationDTO;
import com.project.moflis.dto.location.VerifyLocationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}")
public class LocationController {

    private final LocationApplicationService locationApplicationService;

    public LocationController(LocationApplicationService locationApplicationService) {
        this.locationApplicationService = locationApplicationService;
    }

    @PostMapping("/location")
    public ResponseEntity<LocationDTO> saveLocation(@PathVariable("userId") Integer userId) {
        LocationDTO location = locationApplicationService.processAndSaveLocation(userId);
        return ResponseEntity.ok(location);
    }

    @PostMapping("/location-verify")
    public ResponseEntity<Boolean> checkLocation(@PathVariable("userId") Integer userId, @RequestBody VerifyLocationRequest request) {
        boolean isVerify = locationApplicationService.verifyLocation(userId, request);
        return ResponseEntity.ok(isVerify);
    }

}
