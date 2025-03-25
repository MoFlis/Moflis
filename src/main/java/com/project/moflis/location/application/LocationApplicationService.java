package com.project.moflis.location.application;

import com.project.moflis.location.dto.CoordinatesRequest;
import com.project.moflis.location.dto.LocationResponseDTO;
import com.project.moflis.location.dto.VerifyLocationRequest;
import com.project.moflis.location.service.LocationService;
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
