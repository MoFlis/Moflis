package com.project.moflis.dto.location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyLocationRequest {
    private Integer userId;
    private double latitude;
    private double longitude;
}
