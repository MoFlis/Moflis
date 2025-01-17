package com.project.moflis.mapper;

import com.project.moflis.command.ProfileCommand;
import com.project.moflis.dto.profile.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(source = "user.id", target = "userId")
    ProfilesDTO toProfilesDto(Profiles profiles);

    @Mapping(source = "userId", target = "user.id")
    Profiles toProfiles(ProfilesDTO profilesDto);

    @Mapping(source = "userId", target = "user.id")
    Profiles toProfiles(ProfileCommand command);

    List<ProfilesDTO> toProfilesDtoList(List<Profiles> profilesList);

    List<Profiles> toProfilesList(List<ProfilesDTO> profilesDtoList);

}
