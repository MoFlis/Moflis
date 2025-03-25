package com.project.moflis.profile.mapper;

import com.project.moflis.profile.command.AddProfileCommand;
import com.project.moflis.profile.command.UpdateProfileCommand;
import com.project.moflis.profile.dto.ProfileResponseDTO;
import com.project.moflis.profile.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(source = "user.id", target = "userId")
    ProfileResponseDTO toProfilesDto(Profile profile);

    @Mapping(source = "userId", target = "user.id")
    Profile toProfiles(ProfileResponseDTO profilesDto);

    @Mapping(source = "userId", target = "user.id")
    Profile toProfiles(AddProfileCommand command);

    @Mapping(source = "userId", target = "user.id")
    Profile toProfiles(UpdateProfileCommand command);

    List<ProfileResponseDTO> toProfilesDtoList(List<Profile> profilesList);

    List<Profile> toProfilesList(List<ProfileResponseDTO> profilesDtoList);

}
