package com.project.moflis.service;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import com.project.moflis.entity.User;
import com.project.moflis.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void getProfileSuccess() {
        // given
        int userId = 9999;
        Profiles profile = new Profiles();
        User user = new User();
        user.setId(userId);
        profile.setUser(user);
        profile.setIntro("테스트 프로필");

        Mockito.when(profileRepository.findByUserId(userId)).thenReturn(profile);

        // when
        ProfilesDTO result = profileService.getProfiles(userId);

        // then
        assertNotNull(result);
        assertEquals("테스트 프로필", result.getIntro());
    }

    @Test
    void getProfileFail() {
        // given
        int userId = 9999;
        Mockito.when(profileRepository.findByUserId(userId)).thenReturn(null);

        // when & then
        assertThrows(RuntimeException.class, () -> {
            profileService.getProfiles(userId);
        });
    }

    @Test
    void addProfileSuccess() {
        // given
        ProfilesDTO profileInfo = new ProfilesDTO();
        profileInfo.setUserId(9999);
        profileInfo.setIntro("성공 프로필");

        Profiles savedProfile = new Profiles();
        User user = new User();
        user.setId(9999);
        savedProfile.setUser(user);
        savedProfile.setIntro("성공 프로필");

        Mockito.when(profileRepository.findByUserId(profileInfo.getUserId())).thenReturn(null);
        Mockito.when(profileRepository.save(Mockito.any(Profiles.class))).thenReturn(savedProfile);

        // when
        ProfilesDTO result = profileService.addProfile(profileInfo);

        // then
        assertNotNull(result);
        assertEquals("성공 프로필", result.getIntro());
    }

    @Test
    void addProfileExistingProfile() {
        // given
        ProfilesDTO profileInfo = new ProfilesDTO();
        profileInfo.setUserId(9999);
        profileInfo.setIntro("이미 있는 프로필");

        Profiles existingProfile = new Profiles();
        User user = new User();
        user.setId(9999);
        existingProfile.setUser(user);

        Mockito.when(profileRepository.findByUserId(profileInfo.getUserId())).thenReturn(existingProfile);

        // when & then
        assertThrows(RuntimeException.class, () -> {
            profileService.addProfile(profileInfo);
        });
    }

    @Test
    void updateProfileSuccess() {
        // given
        ProfilesDTO profileInfo = new ProfilesDTO();
        profileInfo.setUserId(9999);
        profileInfo.setIntro("업데이트된 프로필");

        Profiles existingProfile = new Profiles();
        User user = new User();
        user.setId(9999);
        existingProfile.setUser(user);
        existingProfile.setIntro("기존 프로필");

        Mockito.when(profileRepository.findByUserId(profileInfo.getUserId())).thenReturn(existingProfile);
        Mockito.when(profileRepository.save(Mockito.any(Profiles.class))).thenReturn(existingProfile);

        // when
        ProfilesDTO result = profileService.updateProfiles(profileInfo);

        // then
        assertNotNull(result);
        assertEquals("업데이트된 프로필", result.getIntro());
    }

    @Test
    void updateProfileFail() {
        // given
        ProfilesDTO profileInfo = new ProfilesDTO();
        profileInfo.setUserId(9999);
        profileInfo.setIntro("업데이트된 프로필");

        Mockito.when(profileRepository.findByUserId(profileInfo.getUserId())).thenReturn(null);

        // when & then
        assertThrows(RuntimeException.class, () -> {
            profileService.updateProfiles(profileInfo);
        });
    }
}
