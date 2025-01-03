package com.project.moflis.mapper;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import com.project.moflis.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProfileMapperTest {

    private final ProfileMapper profileMapper = Mappers.getMapper(ProfileMapper.class);

    @Test
    public void testToProfileDto() {
        //Given
        Profiles profile = new Profiles();
        profile.setId(1);
        profile.setIntro("테스트");
        profile.setProfileImageName("text_image");
        profile.setTrustScore(50f);
        User user = new User();
        user.setId(1);
        profile.setUser(user);

        ProfilesDTO profilesDTO = profileMapper.toProfilesDto(profile);

        assertThat(profilesDTO.getId()).isEqualTo(1);
        assertThat(profilesDTO.getIntro()).isEqualTo("테스트");
        assertThat(profilesDTO.getProfileImageName()).isEqualTo("text_image");
        assertThat(profilesDTO.getTrustScore()).isEqualTo(50f);
        assertThat(profilesDTO.getUserId()).isEqualTo(1);

    }

    @Test
    public void testToProfile() {
        //Given
        ProfilesDTO profilesDTO = new ProfilesDTO();
        profilesDTO.setId(1);
        profilesDTO.setIntro("테스트");
        profilesDTO.setProfileImageName("text_image");
        profilesDTO.setTrustScore(50f);
        profilesDTO.setUserId(1);

        //when
        Profiles profiles = profileMapper.toProfiles(profilesDTO);

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
        Profiles profiles1 = new Profiles();
        profiles1.setId(1);
        profiles1.setIntro("소개1");
        profiles1.setTrustScore(50f);

        Profiles profiles2 = new Profiles();
        profiles2.setId(2);
        profiles2.setIntro("소개2");
        profiles2.setTrustScore(70f);

        List<Profiles> profilesList = List.of(profiles1, profiles2);

        // When
        List<ProfilesDTO> profilesDTOList = profileMapper.toProfilesDtoList(profilesList);

        // Then
        assertThat(profilesDTOList).hasSize(2);
        assertThat(profilesDTOList.get(0).getIntro()).isEqualTo(profiles1.getIntro());
        assertThat(profilesDTOList.get(1).getIntro()).isEqualTo(profiles2.getIntro());
    }

    @Test
    public void testToProfilesList() {
        // Given
        ProfilesDTO profilesDTO1 = new ProfilesDTO();
        profilesDTO1.setId(1);
        profilesDTO1.setIntro("소개1");
        profilesDTO1.setTrustScore(50f);

        ProfilesDTO profilesDTO2 = new ProfilesDTO();
        profilesDTO2.setId(2);
        profilesDTO2.setIntro("소개2");
        profilesDTO2.setTrustScore(70f);

        List<ProfilesDTO> profilesDTOList = List.of(profilesDTO1, profilesDTO2);

        // When
        List<Profiles> profilesList = profileMapper.toProfilesList(profilesDTOList);

        // Then
        assertThat(profilesList).hasSize(2);
        assertThat(profilesList.get(0).getIntro()).isEqualTo(profilesDTO1.getIntro());
        assertThat(profilesList.get(1).getIntro()).isEqualTo(profilesDTO2.getIntro());
    }


}