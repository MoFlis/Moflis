package com.project.moflis.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.moflis.command.location.LocationCommand;
import com.project.moflis.dto.location.LocationResponseDTO;
import com.project.moflis.entity.Locations;
import com.project.moflis.entity.User;
import com.project.moflis.mapper.LocationMapper;
import com.project.moflis.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class LocationService {

    private static String KAKAO_API_KEY = "4cf3e1d70b6f7847b9079a4dabf3a6d5";

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Map<String, Double> getCoordinates(String address) {
        try {
            // Kakao API URL
            String url =
                    "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);

            // HTTP 요청
            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                    String.class);

            // JSON 응답 파싱
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode documents = root.path("documents");

            // documents 배열이 비어있는지 확인
            if (documents.isEmpty()) {
                throw new RuntimeException("주소에 대한 데이터를 찾을 수 없습니다.");
            }

            // 첫 번째 문서에서 위도와 경도 가져오기
            JsonNode location = documents.get(0);
            Double latitude = Double.valueOf(location.get("y").asText());  // 위도
            Double longitude = Double.valueOf(location.get("x").asText()); // 경도
            Map<String, Double> map = new HashMap<>();
            map.put("latitude", latitude);
            map.put("longitude", longitude);
            return map;

        } catch (Exception e) {
            throw new RuntimeException("주소 변환 중 오류 발생: " + e.getMessage(), e);
        }
    }

    @Transactional
    public LocationResponseDTO saveLocation(Map<String, Double> result, Integer userId) {
        if (locationRepository.existsByUserId(userId)) {
            throw new RuntimeException("이미 저장된 유저 입니다");
        }
        Locations location = new Locations();
        User user = new User();
        user.setId(userId);
        location.setUser(user);
        location.setLatitude(result.get("latitude"));
        location.setLongitude(result.get("longitude"));
        location.setVerified(false);
        location.setRequestTime(LocalDateTime.now());
        return LocationMapper.INSTANCE.toLocationsDTO(locationRepository.save(location));
    }

    @Transactional
    public boolean locationVerify(LocationCommand command) {
        Locations locationInfo = locationRepository.findByUserId(command.getUserId());
        System.out.println(locationInfo);

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
        System.out.println("저장 위도" + userLatitude);
        System.out.println("저장 경도" + userLongitude);

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
