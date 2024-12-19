package com.project.moflis.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfilesDTO {

    private Integer id;
    private Integer userId;
    private String intro;
    private String profileImage;
    private Float trustScore;
    private int locationVerified;

    public ProfilesDTO(Integer id, Integer userId, String intro, String profileImage,
        Float trust_score,
        int locationVerified) {
        this.id = id;
        this.userId = userId;
        this.intro = intro;
        this.profileImage = profileImage;
        this.trustScore = trust_score;
        this.locationVerified = locationVerified;
    }
}
