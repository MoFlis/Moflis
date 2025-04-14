package com.project.moflis.location.mapper;

import com.project.moflis.location.command.LocationCommand;
import com.project.moflis.location.dto.LocationResponseDTO;
import com.project.moflis.location.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationResponseDTO toLocationsDTO(Location locations);

    Location toLocations(LocationResponseDTO locationsDTO);

    Location toLocations(LocationCommand command);

    // 리스트 변환
    List<LocationResponseDTO> toLocationsDTOList(List<Location> locationsList);

    List<Location> toLocationsList(List<LocationResponseDTO> locationsDTOList);

}
