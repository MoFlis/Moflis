package com.project.moflis.dto.location;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LocationDTO {

    private Integer id;
    private Integer userId;
    private double latitude;
    private double longitude;
    private boolean verified;
    private LocalDateTime requestTime;
    private LocalDateTime completedTime;
}
