package com.project.moflis.dto.location;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CoordinatesRequest {
    
    @DecimalMin(value = "-90.0", message = "위도는 최소 -90.0이어야 합니다.")
    @DecimalMax(value = "90.0", message = "위도는 최대 90.0이어야 합니다.")
    private double latitude;

    @DecimalMin(value = "-180.0", message = "경도는 최소 -180.0이어야 합니다.")
    @DecimalMax(value = "180.0", message = "경도는 최대 180.0이어야 합니다.")
    private double longitude;
}
