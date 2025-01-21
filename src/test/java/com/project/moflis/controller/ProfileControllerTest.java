package com.project.moflis.controller;

import com.project.moflis.dto.profile.AddProfileRequest;
import com.project.moflis.dto.profile.ProfileResponseDTO;
import com.project.moflis.dto.profile.UpdateProfileReqeust;
import com.project.moflis.entity.Profile;
import com.project.moflis.entity.User;
import com.project.moflis.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileController profileController;

    @BeforeEach
    void setUp() {
        profileRepository.deleteAll();
        Profile testProfile = new Profile();
        User user = new User();
        user.setId(9999);
        testProfile.setUser(user); // 테스트용 userId
        testProfile.setIntro("테스트 프로필");
        testProfile.setTrustScore(50f);
        testProfile.setLocationVerified(false);
        profileRepository.save(testProfile); // 프로필 데이터 저장
        assertEquals(1, profileRepository.count()); // 초기 상태 확인
    }

    @Test
    void getProfile() throws Exception {
        //given
        int userId = 9999;

        //when
        mockMvc.perform(get("/api/v1/users/{userId}/profile", userId)
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Commit
    void addProfile() throws Exception {
        // given
        int userId = 9998;
        AddProfileRequest addProfileRequest = new AddProfileRequest();
        addProfileRequest.setUserId(userId);
        addProfileRequest.setIntro("안녕 나는 유저1");

        // when
        MvcResult mockResult = mockMvc.perform(post("/api/v1/users/{userId}/profile", userId)
                        .param("userId", String.valueOf(addProfileRequest.getUserId()))
                        .param("intro", addProfileRequest.getIntro()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        assertEquals(200, mockResult.getResponse().getStatus());

        Profile saveProfile = profileRepository.findByUserId(userId);
        assertNotNull(saveProfile);
        assertEquals("안녕 나는 유저1", saveProfile.getIntro());
    }

    @Test
    void updateProfile() throws Exception {
        //given
        int userId = 9999;
        ProfileResponseDTO requestProfilesDTO = new ProfileResponseDTO();
        requestProfilesDTO.setUserId(userId);
        requestProfilesDTO.setIntro("안녕 나는 테스트 유저9999");

        //when
        MvcResult result = mockMvc.perform(patch("/api/v1/users/{userId}/profile", userId)
                        .param("intro", requestProfilesDTO.getIntro())
                        .param("trustScore", String.valueOf(requestProfilesDTO.getTrustScore())))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        System.out.println("응답" + result.getResponse().getContentAsString());

        //then
        assertEquals(200, result.getResponse().getStatus());
        Profile updateProfile = profileRepository.findByUserId(userId);
        assertEquals(requestProfilesDTO.getIntro(), updateProfile.getIntro());
    }

    @Test
    void updateFailProfile() throws Exception {
        // given
        int userId = 9997; // 존재하지 않는 userId
        UpdateProfileReqeust updateProfileReqeust = new UpdateProfileReqeust();
        updateProfileReqeust.setUserId(userId);
        updateProfileReqeust.setIntro("실패할 테스트");

        //when
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            profileController.updateProfile(userId, updateProfileReqeust);
        });

        //then
        assertEquals("아이디에 해당하는 프로필 정보가 없습니다." + userId, thrownException.getMessage());
    }
}