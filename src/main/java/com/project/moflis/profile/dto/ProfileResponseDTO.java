package com.project.moflis.profile.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ProfileResponseDTO {

    private Long id;
    private Long userId;
    private String intro;
    private MultipartFile profileImage;
    private String profileImageName;
    private Float trustScore;
    private boolean locationVerified;
}