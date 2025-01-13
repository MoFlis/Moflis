package com.project.moflis.service;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import com.project.moflis.exception.ImageUploadException;
import com.project.moflis.mapper.ProfileMapper;
import com.project.moflis.repository.ProfileRepository;
import com.project.moflis.storage.FileStorageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final FileStorageService fileStorageService;

    public ProfileService(ProfileRepository profileRepository, FileStorageService fileStorageService) {
        this.profileRepository = profileRepository;
        this.fileStorageService = fileStorageService;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ProfilesDTO getProfiles(int userId) {
        Profiles profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("아이디에 해당하는 프로필 정보가 없습니다: " + userId);
        }
        return ProfileMapper.INSTANCE.toProfilesDto(profile);
    }

    @Transactional
    public ProfilesDTO addProfile(ProfilesDTO profileInfo) {

        Profiles existingProfile = profileRepository.findByUserId(profileInfo.getUserId());

        if (existingProfile != null) {
            throw new RuntimeException("이미 등록되어있는 사용자 입니다 " + profileInfo.getUserId());
        }
        Profiles profile = ProfileMapper.INSTANCE.toProfiles(profileInfo);

        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.save(profile));
    }

    @Transactional
    public ProfilesDTO updateProfiles(ProfilesDTO profileInfo) {
        Profiles existingProfile = profileRepository.findByUserId(profileInfo.getUserId());

        if (existingProfile == null) {
            throw new RuntimeException("아이디에 해당하는 프로필 정보가 없습니다." + profileInfo.getUserId());
        }

        if (profileInfo.getProfileImage() != null && !profileInfo.getProfileImage().isEmpty()) {
            try {
                fileStorageService.createDirectoryIfNotExists(profileInfo.getProfileImage().getOriginalFilename(), uploadDir);

                String originalFilename = profileInfo.getProfileImage().getOriginalFilename();
                String profileImageName = fileStorageService.saveFile(originalFilename, uploadDir, profileInfo.getProfileImage());
                existingProfile.setProfileImageName(profileImageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        existingProfile.setIntro(profileInfo.getIntro());
        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.save(existingProfile));
    }

    @Transactional
    public void addProfileImage(int userId, MultipartFile file) {
        Profiles existingProfile = profileRepository.findByUserId(userId);
        System.out.println(existingProfile);
        try {
            saveProfileImage(file, existingProfile);
        } catch (Exception e) {
            throw new ImageUploadException("프로필 이미지 업로드 실패: " + e.getMessage());
        }
    }

    @Transactional
    public void updateProfileImage(int userId, MultipartFile file) {
        Profiles existingProfile = profileRepository.findByUserId(userId);

        if (existingProfile == null) {
            throw new RuntimeException("아이디에 해당하는 프로필 정보가 없습니다." + userId);
        }

        try {
            saveProfileImage(file, existingProfile);
        } catch (Exception e) {
            throw new ImageUploadException("프로필 이미지 업로드 실패: " + e.getMessage());

        }
    }

    private void saveProfileImage(MultipartFile file, Profiles existingProfile) {
        if (file != null && !file.isEmpty()) {
            try {
                fileStorageService.createDirectoryIfNotExists(file.getOriginalFilename(), uploadDir);
                String originalFilename = file.getOriginalFilename();
                String profileImageName = fileStorageService.saveFile(originalFilename, uploadDir, file);
                existingProfile.setProfileImageName(profileImageName);
                Profiles addResult = profileRepository.save(existingProfile);
                ProfileMapper.INSTANCE.toProfilesDto(addResult);
                return;
            } catch (Exception e) {
                throw new ImageUploadException("프로필 이미지 저장 중 오류 발생: " + e.getMessage());
            }
        }
        throw new IllegalArgumentException("파일이 비어있습니다.");
    }
}
