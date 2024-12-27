package com.project.moflis.service;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import com.project.moflis.mapper.ProfileMapper;
import com.project.moflis.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfilesDTO getProfiles(int userId) {
        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.findByUserId(userId));
    }

    @Transactional
    public ProfilesDTO updateProfiles(ProfilesDTO profileInfo) {
        Profiles existingProfile = profileRepository.findByUserId(profileInfo.getUserId());

        if (existingProfile == null) {
            throw new RuntimeException("아이디에 해당하는 프로필 정보가 없습니다. " + profileInfo.getUserId());
        }

        Profiles profile = ProfileMapper.INSTANCE.toProfiles(profileInfo);
        existingProfile.setIntro(profileInfo.getIntro());
        existingProfile.setProfileImage(profileInfo.getProfileImage());
        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.save(existingProfile));
    }

    @Transactional
    public ProfilesDTO addProfile(ProfilesDTO profilesDTO) {
        Profiles profile = ProfileMapper.INSTANCE.toProfiles(profilesDTO);
        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.save(profile));
    }

    public void uploadImage(Integer userId, String profileImage) {
        Profiles profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("아이디가 존재하지 않습니다");
        }
        profile.setProfileImage(profileImage);
        profileRepository.save(profile);
    }

    public void updateImage(Integer userId, String profileImage) {
        Profiles profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("아이디가 존재하지 않습니다");
        }
        profile.setProfileImage(profileImage);
        profileRepository.save(profile);
    }
}
