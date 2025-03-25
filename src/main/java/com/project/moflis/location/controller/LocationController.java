package com.project.moflis.location.controller;

import com.project.moflis.location.application.LocationApplicationService;
import com.project.moflis.location.dto.CoordinatesRequest;
import com.project.moflis.location.dto.LocationResponseDTO;
import com.project.moflis.location.dto.VerifyLocationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}")
public class LocationController {

    private final LocationApplicationService locationApplicationService;

    public LocationController(LocationApplicationService locationApplicationService) {
        this.locationApplicationService = locationApplicationService;
    }

    @PostMapping("/locations")
    public ResponseEntity<LocationResponseDTO> saveLocation(@PathVariable("userId") Integer userId, @RequestBody CoordinatesRequest coordinatesRequest) {
        LocationResponseDTO location = locationApplicationService.processAndSaveLocation(userId, coordinatesRequest);
        return ResponseEntity.ok(location);
    }

    @PostMapping("/locations/verify")
    public ResponseEntity<Boolean> verifyLocation(@PathVariable("userId") Integer userId, @RequestBody VerifyLocationRequest request) {
        boolean isVerify = locationApplicationService.verifyLocation(userId, request);
        return ResponseEntity.ok(isVerify);
    }

}
