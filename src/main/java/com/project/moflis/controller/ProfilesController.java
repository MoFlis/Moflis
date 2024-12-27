package com.project.moflis.controller;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.service.ProfileService;
import com.project.moflis.util.FileRenameUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProfilesController {

    @Autowired
    private ProfileService profileService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("profile")
    public Map<String, Object> getProfile(@RequestParam("userId") int userId) {
        Map<String, Object> map = new HashMap<>();
        ProfilesDTO profile = profileService.getProfiles(userId);
        map.put("profile", profile);
        return map;
    }

    @PostMapping("add_profile")
    public Map<String, Object> addProfile(ProfilesDTO profilesDTO) {
        Map<String, Object> map = new HashMap<>();
        ProfilesDTO addResult = profileService.addProfile(profilesDTO);
        map.put("addResult", addResult);
        return map;
    }

    @PostMapping("update_profile")
    public Map<String, Object> updateProfile(ProfilesDTO profileInfo) {
        Map<String, Object> map = new HashMap<>();
        ProfilesDTO profile = profileService.updateProfiles(profileInfo);
        map.put("result", profile);
        return map;
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
