package com.project.moflis.location.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class LocationResponseDTO {

    private Long id;
    private Long userId;
    private double latitude;
    private double longitude;
    private boolean verified;
    private LocalDateTime requestTime;
    private LocalDateTime completedTime;
}
