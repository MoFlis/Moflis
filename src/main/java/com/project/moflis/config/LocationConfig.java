package com.project.moflis.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class LocationConfig {

    @Value("${location.base-latitude}")
    private double baseLatitude;

    @Value("${location.base-longitude}")
    private double baseLongitude;

    @Value("${location.verification-radius}")
    private int verificationRadius; // 반지름 (미터)

    @Value("${location.earth-radius}")
    private int earthRadius; // 지구 반지름 (미터)

    @PostConstruct
    public void init() {
        System.out.println("========== Location Configuration ==========");
        System.out.println("Base Latitude: " + baseLatitude);
        System.out.println("Base Longitude: " + baseLongitude);
        System.out.println("Verification Radius: " + verificationRadius + "m");
        System.out.println("Earth Radius: " + earthRadius + "m");
        System.out.println("============================================");
    }

}
