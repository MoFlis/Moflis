package com.project.moflis.location.service;

import com.project.moflis.global.config.LocationConfig;
import com.project.moflis.global.exception.UserLocationAlreadyExistsException;
import com.project.moflis.location.dto.CoordinatesRequest;
import com.project.moflis.location.dto.LocationResponseDTO;
import com.project.moflis.location.dto.VerifyLocationRequest;
import com.project.moflis.location.entity.Locations;
import com.project.moflis.location.mapper.LocationMapper;
import com.project.moflis.location.repository.LocationRepository;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocationService {


    private final LocationRepository locationRepository;
    private final UserService userService;
    private final LocationConfig locationConfig;

    public LocationService(LocationRepository locationRepository, UserService userService, LocationConfig locationConfig) {
        this.locationRepository = locationRepository;
        this.userService = userService;
        this.locationConfig = locationConfig;
    }

    @Transactional
    public LocationResponseDTO saveLocation(Integer userId, CoordinatesRequest coordinates) {
        User user = userService.getUserById(userId);

        if (locationRepository.existsByUserId(userId)) {
            throw new UserLocationAlreadyExistsException("이미 저장된 위치 정보가 존재합니다.");
        }

        Locations location = new Locations(
                null,
                user,
                coordinates.getLatitude(),
                coordinates.getLongitude(),
                false,
                LocalDateTime.now(),
                null
        );
        return LocationMapper.INSTANCE.toLocationsDTO(locationRepository.save(location));
    }

    @Transactional
    public boolean locationVerify(Integer userId, VerifyLocationRequest verifyLocationRequest) {
        Locations locationInfo = locationRepository.findByUserId(userId);

        if (locationInfo == null) {
            throw new RuntimeException("해당 유저의 위치정보가 없습니다");
        }

        // 기준 위치 위도, 경도
        double baseLatitude = locationConfig.getBaseLatitude();
        double baseLongitude = locationConfig.getBaseLongitude();

        //double baseLatitude = verifyLocationRequest.getLatitude(); // 예: 서울
        //double baseLongitude = verifyLocationRequest.getLongitude(); // 예: 서울

        // 저장된 위치 위도, 경도
        double userLatitude = locationInfo.getLatitude();
        double userLongitude = locationInfo.getLongitude();

        double distance = calculateDistance(baseLatitude, baseLongitude, userLatitude,
                userLongitude);

        // 인증 기준거리
        double verificationRadius = locationConfig.getVerificationRadius();

        boolean isVerified = distance <= verificationRadius;
        if (isVerified) {
            locationInfo.setVerified(isVerified);
            locationInfo.setCompletedTime(LocalDateTime.now());
            locationRepository.save(locationInfo);
        }

        // 인증 결과 반환
        return isVerified;
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double EARTH_RADIUS = locationConfig.getEarthRadius(); // 지구 반지름 (미터)

        double latDiff = Math.toRadians(lat2 - lat1); // 위도 차이
        double lonDiff = Math.toRadians(lon2 - lon1); // 경도 차이

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

}
