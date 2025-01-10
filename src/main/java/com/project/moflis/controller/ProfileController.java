package com.project.moflis.controller;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.service.ProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping("/profile")
    public ProfilesDTO getProfile(@PathVariable int userId) {
        ProfilesDTO profile = profileService.getProfiles(userId);
        return profile;
    }

    @PostMapping("/profile")
    public ProfilesDTO addProfile(@PathVariable int userId, ProfilesDTO profilesDTO) {
        profilesDTO.setUserId(userId);
        ProfilesDTO addResult = profileService.addProfile(profilesDTO);
        return addResult;
    }

    @PatchMapping("/profile")
    public ProfilesDTO updateProfile(@PathVariable int userId, ProfilesDTO profileInfo) {
        profileInfo.setUserId(userId);
        ProfilesDTO profile = profileService.updateProfiles(profileInfo);
        return profile;
    }

}
