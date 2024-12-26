package com.project.moflis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfilesDTO {

    private Integer id;
    private Integer userId; // 반드시 존재해야 함
    private String intro;
    private String profileImage;
    private Float trustScore;
    private int locationVerified;
}