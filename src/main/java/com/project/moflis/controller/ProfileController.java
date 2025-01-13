package com.project.moflis.controller;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.service.ProfileService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addProfileImage(@PathVariable int userId, MultipartFile file) {
        profileService.addProfileImage(userId, file);
        return ResponseEntity.ok("프로필이 성공적으로 업데이트 되었습니다.");
    }

    @PatchMapping("/profile")
    public ProfilesDTO updateProfile(@PathVariable int userId, ProfilesDTO profileInfo) {
        profileInfo.setUserId(userId);
        ProfilesDTO profile = profileService.updateProfiles(profileInfo);
        return profile;
    }

    @PatchMapping("/profileImage")
    public ResponseEntity<String> updateProfileImage(@PathVariable int userId, MultipartFile file) {
        profileService.updateProfileImage(userId, file);
        return ResponseEntity.ok("프로필이 성공적으로 업데이트 되었습니다.");
    }

}
