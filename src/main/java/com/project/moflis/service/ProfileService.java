package com.project.moflis.service;

import com.project.moflis.command.profile.AddProfileCommand;
import com.project.moflis.command.profile.UpdateProfileCommand;
import com.project.moflis.dto.profile.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import com.project.moflis.exception.ImageUploadException;
import com.project.moflis.mapper.ProfileMapper;
import com.project.moflis.repository.ProfileRepository;
import com.project.moflis.storage.FileStorageService;
import jakarta.transaction.Transactional;
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

    public ProfilesDTO getProfiles(int userId) {
        Profiles profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("아이디에 해당하는 프로필 정보가 없습니다: " + userId);
        }
        return ProfileMapper.INSTANCE.toProfilesDto(profile);
    }

    @Transactional
    public ProfilesDTO addProfile(AddProfileCommand command) {

        Profiles existingProfile = profileRepository.findByUserId(command.getUserId());

        if (existingProfile != null) {
            throw new RuntimeException("이미 등록되어있는 사용자 입니다 " + command.getUserId());
        }
        Profiles profile = ProfileMapper.INSTANCE.toProfiles(command);
        return ProfileMapper.INSTANCE.toProfilesDto(profileRepository.save(profile));
    }

    @Transactional
    public ProfilesDTO updateProfiles(UpdateProfileCommand command) {
        Profiles existingProfile = profileRepository.findByUserId(command.getUserId());

        if (existingProfile == null) {
            throw new RuntimeException("아이디에 해당하는 프로필 정보가 없습니다." + command.getUserId());
        }
        existingProfile.setIntro(command.getIntro());
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
                String s3Url = fileStorageService.uploadToS3(file.getOriginalFilename(), file);
                existingProfile.setProfileImageName(s3Url);
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
