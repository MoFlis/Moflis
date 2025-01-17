package com.project.moflis.service;

import com.project.moflis.command.ProfileCommand;
import com.project.moflis.dto.profile.ProfilesDTO;
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
        ProfileCommand command = new ProfileCommand(9999, "성공프로필");

        Profiles savedProfile = new Profiles();
        User user = new User();
        user.setId(9999);
        savedProfile.setUser(user);
        savedProfile.setIntro("성공 프로필");

        Mockito.when(profileRepository.findByUserId(command.getUserId())).thenReturn(null);
        Mockito.when(profileRepository.save(Mockito.any(Profiles.class))).thenReturn(savedProfile);

        // when
        ProfilesDTO result = profileService.addProfile(command);

        // then
        assertNotNull(result);
        assertEquals("성공 프로필", result.getIntro());
    }

    @Test
    void addProfileExistingProfile() {
        // given
        ProfileCommand command = new ProfileCommand(9999, "이미 있는 프로필");

        Profiles existingProfile = new Profiles();
        User user = new User();
        user.setId(9999);
        existingProfile.setUser(user);

        Mockito.when(profileRepository.findByUserId(command.getUserId())).thenReturn(existingProfile);

        // when & then
        assertThrows(RuntimeException.class, () -> {
            profileService.addProfile(command);
        });
    }

    @Test
    void updateProfileSuccess() {
        // given
        ProfileCommand command = new ProfileCommand(9999, "업데이트된 프로필");

        Profiles existingProfile = new Profiles();
        User user = new User();
        user.setId(9999);
        existingProfile.setUser(user);
        existingProfile.setIntro("기존 프로필");

        Mockito.when(profileRepository.findByUserId(command.getUserId())).thenReturn(existingProfile);
        Mockito.when(profileRepository.save(Mockito.any(Profiles.class))).thenReturn(existingProfile);

        // when
        ProfilesDTO result = profileService.updateProfiles(command);

        // then
        assertNotNull(result);
        assertEquals("업데이트된 프로필", result.getIntro());
    }

    @Test
    void updateProfileFail() {
        // given
        ProfileCommand command = new ProfileCommand(9999, "업데이트된 프로필");

        Mockito.when(profileRepository.findByUserId(command.getUserId())).thenReturn(null);

        // when & then
        assertThrows(RuntimeException.class, () -> {
            profileService.updateProfiles(command);
        });
    }
}
