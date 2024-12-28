package com.project.moflis.mapper;

import com.project.moflis.dto.LocationDTO;
import com.project.moflis.entity.Locations;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Mapping(source = "user.id", target = "userId")
    LocationDTO toLocationsDTO(Locations locations);

    @Mapping(source = "userId", target = "user.id")
    Locations toLocations(LocationDTO locationsDTO);

    // 리스트 변환
    List<LocationDTO> toLocationsDTOList(List<Locations> locationsList);

    List<Locations> toLocationsList(List<LocationDTO> locationsDTOList);

}
