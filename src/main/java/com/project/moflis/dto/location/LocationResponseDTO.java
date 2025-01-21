package com.project.moflis.dto.location;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class LocationResponseDTO {

    private Integer id;
    private Integer userId;
    private double latitude;
    private double longitude;
    private boolean verified;
    private LocalDateTime requestTime;
    private LocalDateTime completedTime;
}
