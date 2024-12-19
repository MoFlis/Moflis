package com.project.moflis.controller;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.service.ProfileService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private ProfileService profileService;

    @GetMapping("profile")
    public Map<String, Object> getProfile(int userId) {
        Map<String, Object> map = new HashMap<>();
        ProfilesDTO profile = profileService.getProfiles(userId);
        map.put("profile", profile);
        return map;
    }

    @PostMapping("profileImage")
    public Map<String, Object> updateProfileImage() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }


    @PostMapping("update_profile")
    public Map<String, Object> updateProfile(ProfilesDTO profileInfo) {
        Map<String, Object> map = new HashMap<>();
        ProfilesDTO profile = profileService.updateProfiles(profileInfo);
        map.put("result", profile);
        return map;

    }
}
