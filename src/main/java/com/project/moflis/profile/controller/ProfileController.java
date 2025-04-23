package com.project.moflis.profile.controller;

import com.project.moflis.profile.command.AddProfileCommand;
import com.project.moflis.profile.command.UpdateProfileCommand;
import com.project.moflis.profile.dto.AddProfileRequest;
import com.project.moflis.profile.dto.ProfileResponseDTO;
import com.project.moflis.profile.dto.UpdateProfileReqeust;
import com.project.moflis.profile.service.ProfileService;
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
    public ResponseEntity<ProfileResponseDTO> getProfile(@PathVariable int userId) {
        ProfileResponseDTO profile = profileService.getProfiles(userId);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> addProfile(@PathVariable long userId, AddProfileRequest request) {
        AddProfileCommand command = new AddProfileCommand(
                userId,
                request.getIntro()
        );
        ProfileResponseDTO addResult = profileService.addProfile(command);
        return ResponseEntity.ok(addResult);
    }

    @PostMapping("/profileImage")
    public ResponseEntity<String> addProfileImage(@PathVariable int userId, MultipartFile file) {
        profileService.addProfileImage(userId, file);
        return ResponseEntity.ok("프로필이 성공적으로 업데이트 되었습니다.");
    }

    @PatchMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable long userId, UpdateProfileReqeust reqeust) {
        UpdateProfileCommand command = new UpdateProfileCommand(
                userId,
                reqeust.getIntro()
        );
        ProfileResponseDTO profile = profileService.updateProfiles(command);
        return ResponseEntity.ok(profile);
    }

    @PatchMapping("/profileImage")
    public ResponseEntity<String> updateProfileImage(@PathVariable long userId, MultipartFile file) {
        profileService.updateProfileImage(userId, file);
        return ResponseEntity.ok("프로필이 성공적으로 업데이트 되었습니다.");
    }

}
