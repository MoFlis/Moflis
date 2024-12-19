package com.project.moflis.mapper;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(source = "user.id", target = "userId")
    ProfilesDTO toProfilesDto(Profiles profiles);

    @Mapping(source = "userId", target = "user.id")
    Profiles toProfiles(ProfilesDTO profilesDto);

    List<ProfilesDTO> toProfilesDtoList(List<Profiles> profilesList);

    List<Profiles> toProfilesList(List<ProfilesDTO> profilesDtoList);

}
