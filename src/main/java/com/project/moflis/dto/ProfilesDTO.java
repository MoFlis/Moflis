package com.project.moflis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilesDTO {

    private Integer id;
    private Integer userId;
    private String intro;
    private MultipartFile profileImage;
    private String profileImageName;
    private Float trustScore;
    private int locationVerified;
}