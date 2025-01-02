package com.project.moflis.controller;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.service.ProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfilesController {

    private final ProfileService profileService;

    public ProfilesController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping("profile")
    public ProfilesDTO getProfile(@RequestParam("userId") int userId) {
        ProfilesDTO profile = profileService.getProfiles(userId);
        return profile;
    }

    @PostMapping("add_profile")
    public ProfilesDTO addProfile(ProfilesDTO profilesDTO) {
        ProfilesDTO addResult = profileService.addProfile(profilesDTO);
        return addResult;
    }

    @PostMapping("update_profile")
    public ProfilesDTO updateProfile(ProfilesDTO profileInfo) {
        ProfilesDTO profile = profileService.updateProfiles(profileInfo);
        return profile;
    }

}
