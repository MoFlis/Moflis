package com.project.moflis.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.moflis.client.KakaoApiClient;
import com.project.moflis.command.location.LocationCommand;
import com.project.moflis.dto.location.CoordinatesDTO;
import com.project.moflis.dto.location.LocationResponseDTO;
import com.project.moflis.entity.Locations;
import com.project.moflis.entity.User;
import com.project.moflis.exception.AddressNotFoundException;
import com.project.moflis.mapper.LocationMapper;
import com.project.moflis.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocationService {

    private final KakaoApiClient kakaoApiClient;

    private final LocationRepository locationRepository;
    private final UserService userService;

    public LocationService(KakaoApiClient kakaoApiClient, LocationRepository locationRepository, UserService userService) {
        this.kakaoApiClient = kakaoApiClient;
        this.locationRepository = locationRepository;
        this.userService = userService;
    }

    public CoordinatesDTO getCoordinates(String address) {
        try {
            JsonNode root = kakaoApiClient.getAddressData(address);
            JsonNode documents = root.path("documents");

            if (documents.isEmpty()) {
                throw new AddressNotFoundException("주소에 대한 데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            }

            JsonNode location = documents.get(0);
            Double latitude = Double.valueOf(location.get("y").asText());  // 위도
            Double longitude = Double.valueOf(location.get("x").asText()); // 경도
            return new CoordinatesDTO(latitude, longitude);

        } catch (Exception e) {
            throw new RuntimeException("주소 변환 중 오류 발생: " + e.getMessage(), e);
        }
    }

    @Transactional
    public LocationResponseDTO saveLocation(CoordinatesDTO coordinates, Integer userId) {
        User user = userService.getUserById(userId);

        if (locationRepository.existsByUserId(userId)) {
            throw new RuntimeException("이미 저장된 위치 정보가 존재합니다.");
        }
        Locations location = new Locations();
        location.setUser(user);
        location.setLatitude(coordinates.getLatitude());
        location.setLongitude(coordinates.getLongitude());
        location.setVerified(false);
        location.setRequestTime(LocalDateTime.now());
        return LocationMapper.INSTANCE.toLocationsDTO(locationRepository.save(location));
    }

    @Transactional
    public boolean locationVerify(LocationCommand command) {
        Locations locationInfo = locationRepository.findByUserId(command.getUserId());

        if (locationInfo == null) {
            throw new RuntimeException("해당 유저의 위치정보가 없습니다");
        }

        // 기준 위치 위도, 경도
        double baseLatitude = 37.597466; // 예: 서울
        double baseLongitude = 127.094160; // 예: 서울

        //double baseLatitude = location.getLatitude(); // 예: 서울
        //double baseLongitude = location.getLongitude(); // 예: 서울

        // 저장된 위치 위도, 경도
        double userLatitude = locationInfo.getLatitude();
        double userLongitude = locationInfo.getLongitude();

        double distance = calculateDistance(baseLatitude, baseLongitude, userLatitude,
                userLongitude);

        // 인증 기준거리
        double verificationRadius = 1000;

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
        final double EARTH_RADIUS = 6371000; // 지구 반지름 (미터)

        double latDiff = Math.toRadians(lat2 - lat1); // 위도 차이
        double lonDiff = Math.toRadians(lon2 - lon1); // 경도 차이

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

}
