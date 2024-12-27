package com.project.moflis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilesDTO {

    private Integer id;
    private Integer userId;
    private String intro;
    private String profileImage;
    private Float trustScore;
    private int locationVerified;
}