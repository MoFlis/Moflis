package com.project.moflis.controller;

import com.project.moflis.application.LocationApplicationService;
import com.project.moflis.command.location.LocationCommand;
import com.project.moflis.dto.location.CoordinatesRequest;
import com.project.moflis.dto.location.LocationResponseDTO;
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

    @PostMapping("/locations")
    public ResponseEntity<LocationResponseDTO> saveLocation(@PathVariable("userId") Integer userId, @RequestBody CoordinatesRequest coordinatesRequest) {
        LocationResponseDTO location = locationApplicationService.processAndSaveLocation(userId, coordinatesRequest);
        return ResponseEntity.ok(location);
    }

    @PostMapping("/locations/verify")
    public ResponseEntity<Boolean> verifyLocation(@PathVariable("userId") Integer userId, @RequestBody VerifyLocationRequest request) {
        LocationCommand command = new LocationCommand(userId, request.getLatitude(), request.getLongitude());
        boolean isVerify = locationApplicationService.verifyLocation(command);
        return ResponseEntity.ok(isVerify);
    }

}
