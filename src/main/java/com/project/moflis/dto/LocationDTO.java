package com.project.moflis.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private Integer id;
    private Integer userId;
    private double latitude;
    private double longitude;
    private boolean status;
    private LocalDateTime requestTime;
    private LocalDateTime completeTime;
}
