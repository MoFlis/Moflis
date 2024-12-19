package com.project.moflis.service;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import com.project.moflis.mapper.ProfileMapper;
import com.project.moflis.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfilesDTO getProfiles(int userId) {
        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.findByUserId(userId));
    }

    public ProfilesDTO updateProfiles(ProfilesDTO profileInfo) {
        Profiles existingProfile = profileRepository.findByUserId(profileInfo.getUserId());

        if (existingProfile == null) {
            throw new RuntimeException("Profile not found with userId: " + profileInfo.getUserId());
        }

        Profiles profile = ProfileMapper.INSTANCE.toProfiles(profileInfo);
        existingProfile.setIntro(profileInfo.getIntro());
        existingProfile.setProfileImage(profileInfo.getProfileImage());
        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.save(existingProfile));
    }
}
