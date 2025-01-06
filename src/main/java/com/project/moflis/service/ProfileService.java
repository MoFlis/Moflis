package com.project.moflis.service;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import com.project.moflis.mapper.ProfileMapper;
import com.project.moflis.repository.ProfileRepository;
import com.project.moflis.util.FileNameConflictResolver;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ProfilesDTO getProfiles(int userId) {
        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.findByUserId(userId));
    }

    @Transactional
    public ProfilesDTO addProfile(ProfilesDTO profileInfo) {

        Profiles existingProfile = profileRepository.findByUserId(profileInfo.getUserId());

        if (existingProfile != null) {
            throw new RuntimeException("이미 등록되어있는 사용자 입니다 " + profileInfo.getUserId());
        }

        Profiles profile = ProfileMapper.INSTANCE.toProfiles(profileInfo);
        if (profileInfo.getProfileImage() != null && !profileInfo.getProfileImage().isEmpty()) {
            try {
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String originalFilename = profileInfo.getProfileImage().getOriginalFilename();
                if (FileNameConflictResolver.isFileNameConflict(originalFilename, uploadDir)) {
                    String safeFilename = FileNameConflictResolver.generateUniqueFileName(originalFilename,
                            uploadDir);
                    String profileImageName = uploadDir + "/" + safeFilename;
                    profileInfo.getProfileImage().transferTo(new File(profileImageName));
                    profile.setProfileImageName(profileImageName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.save(profile));
    }

    @Transactional
    public ProfilesDTO updateProfiles(ProfilesDTO profileInfo) {
        Profiles existingProfile = profileRepository.findByUserId(profileInfo.getUserId());

        if (existingProfile == null) {
            throw new RuntimeException("아이디에 해당하는 프로필 정보가 없습니다. " + profileInfo.getUserId());
        }

        if (profileInfo.getProfileImage() != null && !profileInfo.getProfileImage().isEmpty()) {
            try {
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String originalFilename = profileInfo.getProfileImage().getOriginalFilename();
                if (FileNameConflictResolver.isFileNameConflict(originalFilename, uploadDir)) {
                    String safeFilename = FileNameConflictResolver.generateUniqueFileName(originalFilename,
                            uploadDir);
                    String profileImageName = uploadDir + "/" + safeFilename;
                    profileInfo.getProfileImage().transferTo(new File(profileImageName));
                    existingProfile.setProfileImageName(profileImageName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        existingProfile.setIntro(profileInfo.getIntro());
        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.save(existingProfile));
    }

}
