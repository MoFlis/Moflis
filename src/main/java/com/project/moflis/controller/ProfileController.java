package com.project.moflis.controller;

import com.project.moflis.command.ProfileCommand;
import com.project.moflis.dto.profile.AddProfileRequest;
import com.project.moflis.dto.profile.ProfilesDTO;
import com.project.moflis.dto.profile.UpdateProfileReqeust;
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
    public ResponseEntity<ProfilesDTO> getProfile(@PathVariable int userId) {
        ProfilesDTO profile = profileService.getProfiles(userId);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfilesDTO> addProfile(@PathVariable int userId, AddProfileRequest request) {
        ProfileCommand command = new ProfileCommand(
                userId,
                request.getIntro()
        );
        ProfilesDTO addResult = profileService.addProfile(command);
        return ResponseEntity.ok(addResult);
    }

    @PostMapping("/profileImage")
    public ResponseEntity<String> addProfileImage(@PathVariable int userId, MultipartFile file) {
        profileService.addProfileImage(userId, file);
        return ResponseEntity.ok("프로필이 성공적으로 업데이트 되었습니다.");
    }

    @PatchMapping("/profile")
    public ResponseEntity<ProfilesDTO> updateProfile(@PathVariable int userId, UpdateProfileReqeust reqeust) {
        ProfileCommand command = new ProfileCommand(
                userId,
                reqeust.getIntro()
        );
        ProfilesDTO profile = profileService.updateProfiles(command);
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/profileImage")
    public ResponseEntity<String> updateProfileImage(@PathVariable int userId, MultipartFile file) {
        profileService.updateProfileImage(userId, file);
        return ResponseEntity.ok("프로필이 성공적으로 업데이트 되었습니다.");
    }

}
