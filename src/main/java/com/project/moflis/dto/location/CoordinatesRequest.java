package com.project.moflis.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CoordinatesRequest {
    private double latitude;
    private double longitude;
}
