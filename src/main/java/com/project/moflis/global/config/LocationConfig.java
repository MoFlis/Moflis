package com.project.moflis.global.config;

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

}
