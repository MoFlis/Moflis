package com.project.moflis.mapper;

import com.project.moflis.profile.dto.ProfileResponseDTO;
import com.project.moflis.profile.entity.Profile;
import com.project.moflis.profile.mapper.ProfileMapper;
import com.project.moflis.user.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileMapperTest {

    private final ProfileMapper profileMapper = Mappers.getMapper(ProfileMapper.class);

    @Test
    public void testToProfileDto() {
        //Given
        Profile profile = new Profile();
        profile.setId(1);
        profile.setIntro("테스트");
        profile.setProfileImageName("text_image");
        profile.setTrustScore(50f);
        User user = new User();
        user.setId(1);
        profile.setUser(user);

        ProfileResponseDTO profilesDTO = profileMapper.toProfilesDto(profile);

        assertThat(profilesDTO.getId()).isEqualTo(1);
        assertThat(profilesDTO.getIntro()).isEqualTo("테스트");
        assertThat(profilesDTO.getProfileImageName()).isEqualTo("text_image");
        assertThat(profilesDTO.getTrustScore()).isEqualTo(50f);
        assertThat(profilesDTO.getUserId()).isEqualTo(1);

    }

    @Test
    public void testToProfile() {
        //Given
        ProfileResponseDTO profilesDTO = new ProfileResponseDTO();
        profilesDTO.setId(1);
        profilesDTO.setIntro("테스트");
        profilesDTO.setProfileImageName("text_image");
        profilesDTO.setTrustScore(50f);
        profilesDTO.setUserId(1);

        //when
        Profile profiles = profileMapper.toProfiles(profilesDTO);

        //then
        assertThat(profiles.getId()).isEqualTo(1);
        assertThat(profiles.getIntro()).isEqualTo("테스트");
        assertThat(profiles.getProfileImageName()).isEqualTo("text_image");
        assertThat(profiles.getTrustScore()).isEqualTo(50f);
        assertThat(profiles.getUser().getId()).isEqualTo(1);
    }

    @Test
    public void testToProfilesDtoList() {
        // Given
        Profile profiles1 = new Profile();
        profiles1.setId(1);
        profiles1.setIntro("소개1");
        profiles1.setTrustScore(50f);

        Profile profiles2 = new Profile();
        profiles2.setId(2);
        profiles2.setIntro("소개2");
        profiles2.setTrustScore(70f);

        List<Profile> profilesList = List.of(profiles1, profiles2);

        // When
        List<ProfileResponseDTO> profilesDTOList = profileMapper.toProfilesDtoList(profilesList);

        // Then
        assertThat(profilesDTOList).hasSize(2);
        assertThat(profilesDTOList.get(0).getIntro()).isEqualTo(profiles1.getIntro());
        assertThat(profilesDTOList.get(1).getIntro()).isEqualTo(profiles2.getIntro());
    }

    @Test
    public void testToProfilesList() {
        // Given
        ProfileResponseDTO profilesDTO1 = new ProfileResponseDTO();
        profilesDTO1.setId(1);
        profilesDTO1.setIntro("소개1");
        profilesDTO1.setTrustScore(50f);

        ProfileResponseDTO profilesDTO2 = new ProfileResponseDTO();
        profilesDTO2.setId(2);
        profilesDTO2.setIntro("소개2");
        profilesDTO2.setTrustScore(70f);

        List<ProfileResponseDTO> profilesDTOList = List.of(profilesDTO1, profilesDTO2);

        // When
        List<Profile> profilesList = profileMapper.toProfilesList(profilesDTOList);

        // Then
        assertThat(profilesList).hasSize(2);
        assertThat(profilesList.get(0).getIntro()).isEqualTo(profilesDTO1.getIntro());
        assertThat(profilesList.get(1).getIntro()).isEqualTo(profilesDTO2.getIntro());
    }


}