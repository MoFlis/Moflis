package com.project.moflis.controller;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.service.ProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfilesController {

    private final ProfileService profileService;

    public ProfilesController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping("/{userId}")
    public ProfilesDTO getProfile(@PathVariable int userId) {
        ProfilesDTO profile = profileService.getProfiles(userId);
        return profile;
    }

    @PostMapping("/{userId}")
    public ProfilesDTO addProfile(@PathVariable int userId, ProfilesDTO profilesDTO) {
        profilesDTO.setUserId(userId);
        ProfilesDTO addResult = profileService.addProfile(profilesDTO);
        return addResult;
    }

    @PutMapping("/{userId}")
    public ProfilesDTO updateProfile(@PathVariable int userId, ProfilesDTO profileInfo) {
        profileInfo.setUserId(userId);
        ProfilesDTO profile = profileService.updateProfiles(profileInfo);
        return profile;
    }

}
