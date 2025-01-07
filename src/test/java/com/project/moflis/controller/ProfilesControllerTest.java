package com.project.moflis.controller;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProfilesControllerTest {

    @Autowired
    private ProfileController profilesController;

    @Autowired
    private ProfileService profileService;

    @Test
    public void testGetProfileWithExistingData() {
        // Given

        // When
        ProfilesDTO profile = profilesController.getProfile(1);
        System.out.println(profile);

        // Then
        assertThat(profile).isNotNull();
        assertThat(profile.getUserId()).isEqualTo(1);
        assertThat(profile.getIntro()).isEqualTo("안녕하세요 유저1입니다"); // 데이터베이스 값에 따라 수정
        assertThat(profile.getProfileImage()).isNull(); // 예상 값
        assertThat(profile.getTrustScore()).isEqualTo(50); // 예상 값
    }

    @Test
    public void testAddProfile() {
        // Given
        int userId = 1;
        ProfilesDTO inputDTO = new ProfilesDTO();
        inputDTO.setIntro("안녕하세요 유저1입니다");
        inputDTO.setTrustScore(50f);

        // When
        ProfilesDTO outputDTO = profilesController.addProfile(userId, inputDTO);
        System.out.println(outputDTO);

        // Then
        assertThat(outputDTO).isNotNull();
        assertThat(outputDTO.getUserId()).isEqualTo(userId);
        assertThat(outputDTO.getIntro()).isEqualTo("안녕하세요 유저1입니다");
        assertThat(outputDTO.getProfileImage()).isNull();
        assertThat(outputDTO.getTrustScore()).isEqualTo(50f);
    }

    @Test
    public void testUpdateProfileWithExistingDataSuccess() {
        // Given
        int userId = 1;

        // DB에 이미 userId=1
        ProfilesDTO existingProfile = profileService.getProfiles(userId);
        assertThat(existingProfile).isNotNull(); // 데이터 존재 여부 확인
        System.out.println("기존 데이터: " + existingProfile);

        ProfilesDTO updateDTO = new ProfilesDTO();
        updateDTO.setIntro("업데이트된 소개입니다 테스트");

        // When
        ProfilesDTO updatedProfile = profilesController.updateProfile(userId, updateDTO);
        System.out.println(updatedProfile);

        // Then
        assertThat(updatedProfile).isNotNull();
        assertThat(updatedProfile.getUserId()).isEqualTo(userId);
        assertThat(updatedProfile.getIntro()).isEqualTo("업데이트된 소개입니다 테스트");

        // DB에서 업데이트된 데이터를 직접 검증
        ProfilesDTO dbProfile = profileService.getProfiles(userId);
        assertThat(dbProfile).isNotNull();
        assertThat(dbProfile.getUserId()).isEqualTo(userId);
        assertThat(dbProfile.getIntro()).isEqualTo("업데이트된 소개입니다 테스트");
    }

    @Test
    public void testUpdateProfileWithExistingDataFail() {
        // DB에 없는 userId조회
        int userId = 99;
        // 업데이트할 데이터 설정
        ProfilesDTO updateDTO = new ProfilesDTO();
        updateDTO.setIntro("테스트 업데이트");

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profilesController.updateProfile(userId, updateDTO);
        });

        // 예외 메시지 검증
        assertThat(exception.getMessage()).contains("아이디에 해당하는 프로필 정보가 없습니다");
    }

}
