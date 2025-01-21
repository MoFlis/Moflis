package com.project.moflis.dto.profile;

import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
public class ProfileResponseDTO {

    private Integer id;
    private Integer userId;
    private String intro;
    private MultipartFile profileImage;
    private String profileImageName;
    private Float trustScore;
    private boolean locationVerified;
}