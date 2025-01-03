package com.project.moflis.mapper;

import com.project.moflis.dto.ProfilesDTO;
import com.project.moflis.entity.Profiles;
import com.project.moflis.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-03T17:16:07+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public ProfilesDTO toProfilesDto(Profiles profiles) {
        if ( profiles == null ) {
            return null;
        }

        ProfilesDTO profilesDTO = new ProfilesDTO();

        profilesDTO.setUserId( profilesUserId( profiles ) );
        profilesDTO.setId( profiles.getId() );
        profilesDTO.setIntro( profiles.getIntro() );
        profilesDTO.setProfileImageName( profiles.getProfileImageName() );
        profilesDTO.setTrustScore( profiles.getTrustScore() );
        profilesDTO.setLocationVerified( profiles.isLocationVerified() );

        return profilesDTO;
    }

    @Override
    public Profiles toProfiles(ProfilesDTO profilesDto) {
        if ( profilesDto == null ) {
            return null;
        }

        Profiles profiles = new Profiles();

        profiles.setUser( profilesDTOToUser( profilesDto ) );
        profiles.setId( profilesDto.getId() );
        profiles.setIntro( profilesDto.getIntro() );
        profiles.setProfileImageName( profilesDto.getProfileImageName() );
        profiles.setTrustScore( profilesDto.getTrustScore() );
        profiles.setLocationVerified( profilesDto.isLocationVerified() );

        return profiles;
    }

    @Override
    public List<ProfilesDTO> toProfilesDtoList(List<Profiles> profilesList) {
        if ( profilesList == null ) {
            return null;
        }

        List<ProfilesDTO> list = new ArrayList<ProfilesDTO>( profilesList.size() );
        for ( Profiles profiles : profilesList ) {
            list.add( toProfilesDto( profiles ) );
        }

        return list;
    }

    @Override
    public List<Profiles> toProfilesList(List<ProfilesDTO> profilesDtoList) {
        if ( profilesDtoList == null ) {
            return null;
        }

        List<Profiles> list = new ArrayList<Profiles>( profilesDtoList.size() );
        for ( ProfilesDTO profilesDTO : profilesDtoList ) {
            list.add( toProfiles( profilesDTO ) );
        }

        return list;
    }

    private Integer profilesUserId(Profiles profiles) {
        if ( profiles == null ) {
            return null;
        }
        User user = profiles.getUser();
        if ( user == null ) {
            return null;
        }
        Integer id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User profilesDTOToUser(ProfilesDTO profilesDTO) {
        if ( profilesDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( profilesDTO.getUserId() );

        return user;
    }
}
