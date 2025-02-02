package com.project.moflis.service;

import com.project.moflis.command.profile.AddProfileCommand;
import com.project.moflis.command.profile.UpdateProfileCommand;
import com.project.moflis.dto.profile.ProfileResponseDTO;
import com.project.moflis.entity.Profile;
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
        Profile profile = new Profile();
        User user = new User();
        user.setId(userId);
        profile.setUser(user);
        profile.setIntro("테스트 프로필");

        Mockito.when(profileRepository.findByUserId(userId)).thenReturn(profile);

        // when
        ProfileResponseDTO result = profileService.getProfiles(userId);

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
        AddProfileCommand command = new AddProfileCommand(9999, "성공프로필");

        Profile savedProfile = new Profile();
        User user = new User();
        user.setId(9999);
        savedProfile.setUser(user);
        savedProfile.setIntro("성공 프로필");

        Mockito.when(profileRepository.findByUserId(command.getUserId())).thenReturn(null);
        Mockito.when(profileRepository.save(Mockito.any(Profile.class))).thenReturn(savedProfile);

        // when
        ProfileResponseDTO result = profileService.addProfile(command);

        // then
        assertNotNull(result);
        assertEquals("성공 프로필", result.getIntro());
    }

    @Test
    void addProfileExistingProfile() {
        // given
        AddProfileCommand command = new AddProfileCommand(9999, "이미 있는 프로필");

        Profile existingProfile = new Profile();
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
        UpdateProfileCommand command = new UpdateProfileCommand(9999, "업데이트된 프로필");

        Profile existingProfile = new Profile();
        User user = new User();
        user.setId(9999);
        existingProfile.setUser(user);
        existingProfile.setIntro("기존 프로필");

        Mockito.when(profileRepository.findByUserId(command.getUserId())).thenReturn(existingProfile);
        Mockito.when(profileRepository.save(Mockito.any(Profile.class))).thenReturn(existingProfile);

        // when
        ProfileResponseDTO result = profileService.updateProfiles(command);

        // then
        assertNotNull(result);
        assertEquals("업데이트된 프로필", result.getIntro());
    }

    @Test
    void updateProfileFail() {
        // given
        UpdateProfileCommand command = new UpdateProfileCommand(9999, "업데이트된 프로필");

        Mockito.when(profileRepository.findByUserId(command.getUserId())).thenReturn(null);

        // when & then
        assertThrows(RuntimeException.class, () -> {
            profileService.updateProfiles(command);
        });
    }
}
