package com.project.moflis.controller;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.service.ProfileService;
import com.project.moflis.util.FileRenameUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProfilesController {

    private final ProfileService profileService;

    public ProfilesController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("profile")
    public ProfilesDTO getProfile(@RequestParam("userId") int userId) {
        ProfilesDTO profile = profileService.getProfiles(userId);
        return profile;
    }

    @PostMapping("add_profile")
    public ProfilesDTO addProfile(ProfilesDTO profilesDTO) {
        Map<String, Object> map = new HashMap<>();
        ProfilesDTO addResult = profileService.addProfile(profilesDTO);
        return addResult;
    }

    @PostMapping("update_profile")
    public ProfilesDTO updateProfile(ProfilesDTO profileInfo) {
        Map<String, Object> map = new HashMap<>();
        ProfilesDTO profile = profileService.updateProfiles(profileInfo);
        return profile;
    }

    @PostMapping("add_profileImage")
    public Map<String, Object> addProfileImage(
        @RequestParam("profileImage") MultipartFile file,
        @RequestParam("userId") Integer userId) {
        Map<String, Object> map = new HashMap<>();
        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            String safeFilename = FileRenameUtil.checkSameFileName(originalFilename, uploadDir);
            String profileImage = uploadDir + "/" + safeFilename;
            file.transferTo(new File(profileImage));
            profileService.uploadImage(userId, profileImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @PostMapping("update_profileImage")
    public Map<String, Object> updateProfileImage(
        @RequestParam("profileImage") MultipartFile file,
        @RequestParam("userId") Integer userId) {
        Map<String, Object> map = new HashMap<>();
        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            String safeFilename = FileRenameUtil.checkSameFileName(originalFilename, uploadDir);
            String profileImage = uploadDir + "/" + safeFilename;
            file.transferTo(new File(profileImage));
            profileService.updateImage(userId, profileImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
