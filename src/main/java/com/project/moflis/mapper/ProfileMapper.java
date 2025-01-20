package com.project.moflis.mapper;

import com.project.moflis.command.profile.AddProfileCommand;
import com.project.moflis.command.profile.UpdateProfileCommand;
import com.project.moflis.dto.profile.ProfileResponseDTO;
import com.project.moflis.entity.Profiles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(source = "user.id", target = "userId")
    ProfileResponseDTO toProfilesDto(Profiles profiles);

    @Mapping(source = "userId", target = "user.id")
    Profiles toProfiles(ProfileResponseDTO profilesDto);

    @Mapping(source = "userId", target = "user.id")
    Profiles toProfiles(AddProfileCommand command);

    @Mapping(source = "userId", target = "user.id")
    Profiles toProfiles(UpdateProfileCommand command);

    List<ProfileResponseDTO> toProfilesDtoList(List<Profiles> profilesList);

    List<Profiles> toProfilesList(List<ProfileResponseDTO> profilesDtoList);

}
