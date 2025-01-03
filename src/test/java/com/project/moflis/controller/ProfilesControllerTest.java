package com.project.moflis.controller;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProfilesControllerTest {

    @Autowired
    private ProfilesController profilesController;

    @Autowired
    private ProfileService profileService;

    @Test
    public void testGetProfileWithExistingData() {
        // Given: 데이터베이스에 이미 ID가 1인 데이터가 존재한다고 가정

        // When: 컨트롤러의 getProfile 메서드를 호출
        ProfilesDTO profile = profilesController.getProfile(1);
        System.out.println(profile);

        // Then: 반환된 ProfilesDTO를 검증
        assertThat(profile).isNotNull();
        assertThat(profile.getUserId()).isEqualTo(1);
        assertThat(profile.getIntro()).isEqualTo("안녕나는 김민쪙"); // 데이터베이스 값에 따라 수정
        assertThat(profile.getProfileImage()).isNull(); // 예상 값
        assertThat(profile.getTrustScore()).isEqualTo(50); // 예상 값
    }
}
