package com.project.moflis.application;

import com.project.moflis.dto.location.CoordinatesRequest;
import com.project.moflis.dto.location.LocationResponseDTO;
import com.project.moflis.dto.location.VerifyLocationRequest;
import com.project.moflis.service.LocationService;
import org.springframework.stereotype.Service;

@Service
public class LocationApplicationService {
    private final LocationService locationService;

    public LocationApplicationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public LocationResponseDTO processAndSaveLocation(Integer userId, CoordinatesRequest coordinatesRequest) {
        return locationService.saveLocation(userId, coordinatesRequest);
    }

    public boolean verifyLocation(Integer userId, VerifyLocationRequest verifyLocationRequest) {
        return locationService.locationVerify(userId, verifyLocationRequest);
    }


}
