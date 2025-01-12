package com.project.moflis.controller;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.service.ProfileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/profileImage")
    public int addProfileImage(@PathVariable int userId, MultipartFile file) {
        ProfilesDTO profile = profileService.addProfileImage(userId, file);
        int result = 0;
        if (profile != null) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    @PatchMapping("/profile")
    public ProfilesDTO updateProfile(@PathVariable int userId, ProfilesDTO profileInfo) {
        profileInfo.setUserId(userId);
        ProfilesDTO profile = profileService.updateProfiles(profileInfo);
        return profile;
    }

    @PatchMapping("/profileImage")
    public int updateProfileImage(@PathVariable int userId, MultipartFile file) {
        ProfilesDTO profile = profileService.updateProfileImage(userId, file);
        int result = 0;
        if (profile != null) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

}
