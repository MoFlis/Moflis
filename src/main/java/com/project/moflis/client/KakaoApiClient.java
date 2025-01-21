package com.project.moflis.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoApiClient {

    private static final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private final String KAKAO_API_KEY = "KakaoAK " + "4cf3e1d70b6f7847b9079a4dabf3a6d5";

    public JsonNode getAddressData(String address) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", KAKAO_API_KEY);

            String url = KAKAO_API_URL + "?query=" + address;

            HttpEntity<String> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readTree(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("카카오 API 호출 중 오류 발생: " + e.getMessage(), e);
        }
    }

}
