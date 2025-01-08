package com.project.moflis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import com.project.moflis.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.web.servlet.MvcResult;

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

    @BeforeEach
    void setUp() {
        profileRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        profileRepository.deleteAll();
    }

    @Test
    void getProfile() throws Exception {
        //given
        int userId = 1;

        //when
        MvcResult result = mockMvc.perform(get("/api/v1/users/{userId}/profile", userId)
                        .param("userId", String.valueOf(userId)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        // then
        String jsonResponse = result.getResponse().getContentAsString(); // JSON 응답 가져오기
        ObjectMapper objectMapper = new ObjectMapper();
        ProfilesDTO profilesResult = objectMapper.readValue(jsonResponse, ProfilesDTO.class);
        assertEquals(userId, profilesResult.getId());
        assertNotNull(profilesResult.getIntro());
    }

    @Test
    void addProfile() throws Exception {
        //given
        int userId = 9998;
        ProfilesDTO requestProfilesDTO = new ProfilesDTO();
        requestProfilesDTO.setUserId(userId);
        requestProfilesDTO.setIntro("안녕 나는 유저1");
        requestProfilesDTO.setProfileImageName(null);
        requestProfilesDTO.setProfileImage(null);
        requestProfilesDTO.setTrustScore(50f);
        requestProfilesDTO.setLocationVerified(false);

        ProfilesDTO responesProfilesDTO = new ProfilesDTO();
        responesProfilesDTO.setUserId(userId);
        responesProfilesDTO.setIntro("안녕 나는 유저1");
        responesProfilesDTO.setProfileImageName(null);
        responesProfilesDTO.setProfileImage(null);
        responesProfilesDTO.setTrustScore(50f);
        responesProfilesDTO.setLocationVerified(false);

        //when
        MvcResult mockResult = mockMvc.perform(post("/api/v1/users/{userId}/profile",userId)
                        .param("userId", String.valueOf(requestProfilesDTO.getUserId()))
                        .param("intro",requestProfilesDTO.getIntro())
                        .param("trustScore", String.valueOf(requestProfilesDTO.getTrustScore()))
                        .param("locationVerified", String.valueOf(requestProfilesDTO.isLocationVerified())))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        //Then
        assertEquals(200, mockResult.getResponse().getStatus());
        Profiles saveProfile = profileRepository.findAll().get(0);
        assertEquals(responesProfilesDTO.getIntro(), saveProfile.getIntro());

    }

    @Test
    void updateProfile() throws Exception {
        //given
        int userId = 9998;
        ProfilesDTO requestProfilesDTO = new ProfilesDTO();
        requestProfilesDTO.setUserId(userId);
        requestProfilesDTO.setIntro("안녕 나는 테스트 유저9999");
        requestProfilesDTO.setProfileImageName(null);
        requestProfilesDTO.setProfileImage(null);
        requestProfilesDTO.setTrustScore(50f);
        requestProfilesDTO.setLocationVerified(false);

        //when
        MvcResult result = mockMvc.perform(patch("/api/v1/users/{userId}/profile",userId)
                .param("intro",requestProfilesDTO.getIntro())
                .param("trustScore", String.valueOf(requestProfilesDTO.getTrustScore()))
                .param("locationVerified", String.valueOf(requestProfilesDTO.isLocationVerified())))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        System.out.println("응답" + result.getResponse().getContentAsString());

        //then
        assertEquals(200, result.getResponse().getStatus());
        Profiles updateProfile = profileRepository.findByUserId(userId);
        assertEquals(requestProfilesDTO.getIntro(), updateProfile.getIntro());
        assertEquals(requestProfilesDTO.getTrustScore(), updateProfile.getTrustScore());
    }
}