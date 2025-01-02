package com.project.moflis.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class ProfilesDTO {

    private Integer id;
    private Integer userId;
    private String intro;
    private MultipartFile profileImage;
    private String profileImageName;
    private Float trustScore;
    private boolean locationVerified;
}