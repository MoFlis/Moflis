package com.project.moflis.mapper;

import com.project.moflis.command.profile.AddProfileCommand;
import com.project.moflis.command.profile.UpdateProfileCommand;
import com.project.moflis.dto.profile.ProfileResponseDTO;
import com.project.moflis.entity.Profile;
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
