package com.project.moflis.mapper;

import com.project.moflis.dto.location.LocationDTO;
import com.project.moflis.entity.Locations;
import com.project.moflis.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LocationMapperTest {

    private final LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    @Test
    void toLocationsDTO() {

        LocalDateTime now = LocalDateTime.of(2025, 1, 10, 15, 16, 4);
        LocalDateTime tomorrow = now.plusDays(1);
        //Given
        Locations location = new Locations();
        location.setId(1);
        User user = new User();
        user.setId(1);
        location.setUser(user);
        location.setVerified(false);
        location.setCompletedTime(tomorrow);
        location.setRequestTime(now);
        location.setLongitude(127.09416);
        location.setLatitude(37.597466);

        LocationDTO locationDTO = locationMapper.toLocationsDTO(location);

        assertThat(locationDTO.getId()).isEqualTo(1);
        assertThat(locationDTO.getUserId()).isEqualTo(1);
        assertThat(locationDTO.isVerified()).isEqualTo(false);
        assertThat(locationDTO.getCompletedTime()).isEqualTo(tomorrow);
        assertThat(locationDTO.getRequestTime()).isEqualTo(now);
        assertThat(locationDTO.getLongitude()).isEqualTo(127.09416);
        assertThat(locationDTO.getLatitude()).isEqualTo(37.597466);

    }

    @Test
    void toLocations() {
        LocalDateTime now = LocalDateTime.of(2025, 1, 10, 15, 16, 4);
        LocalDateTime tomorrow = now.plusDays(1);
        //Given
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(1);
        locationDTO.setUserId(1);
        locationDTO.setVerified(false);
        locationDTO.setCompletedTime(tomorrow);
        locationDTO.setRequestTime(now);
        locationDTO.setLongitude(127.09416);
        locationDTO.setLatitude(37.597466);

        Locations location = locationMapper.toLocations(locationDTO);

        assertThat(location.getId()).isEqualTo(1);
        assertThat(location.getUser().getId()).isEqualTo(1);
        assertThat(location.getVerified()).isEqualTo(false);
        assertThat(location.getCompletedTime()).isEqualTo(tomorrow);
        assertThat(location.getRequestTime()).isEqualTo(now);
        assertThat(location.getLongitude()).isEqualTo(127.09416);
        assertThat(location.getLatitude()).isEqualTo(37.597466);
    }

    @Test
    void toLocationsListDTO() {

        LocalDateTime now = LocalDateTime.of(2025, 1, 10, 15, 16, 4);
        LocalDateTime tomorrow = now.plusDays(1);

        Locations location1 = new Locations();
        location1.setId(1);
        User user = new User();
        user.setId(1);
        location1.setUser(user);
        location1.setVerified(false);
        location1.setCompletedTime(tomorrow);
        location1.setRequestTime(now);
        location1.setLongitude(127.09416);
        location1.setLatitude(37.597466);

        Locations location2 = new Locations();
        location2.setId(2);
        User user2 = new User();
        user2.setId(2);
        location2.setUser(user2);
        location2.setVerified(false);
        location2.setCompletedTime(tomorrow);
        location2.setRequestTime(now);
        location2.setLongitude(127.09416);
        location2.setLatitude(37.597466);

        List<LocationDTO> locationDTOList = new ArrayList<>();
        locationDTOList.add(locationMapper.toLocationsDTO(location1));
        locationDTOList.add(locationMapper.toLocationsDTO(location2));
        assertThat(locationDTOList).hasSize(2);
        assertThat(locationDTOList.get(0).getId()).isEqualTo(1);
        assertThat(locationDTOList.get(1).getId()).isEqualTo(2);
        assertThat(locationDTOList.get(0).getUserId()).isEqualTo(1);
        assertThat(locationDTOList.get(1).getUserId()).isEqualTo(2);
    }

    @Test
    void toLocationsList() {

        LocalDateTime now = LocalDateTime.of(2025, 1, 10, 15, 16, 4);
        LocalDateTime tomorrow = now.plusDays(1);

        LocationDTO locationDTO1 = new LocationDTO();
        locationDTO1.setId(1);
        locationDTO1.setUserId(1);
        locationDTO1.setVerified(false);
        locationDTO1.setCompletedTime(tomorrow);
        locationDTO1.setRequestTime(now);
        locationDTO1.setLongitude(127.09416);
        locationDTO1.setLatitude(37.597466);

        LocationDTO locationDTO2 = new LocationDTO();
        locationDTO2.setId(2);
        locationDTO2.setUserId(2);
        locationDTO2.setVerified(false);
        locationDTO2.setCompletedTime(tomorrow);
        locationDTO2.setRequestTime(now);
        locationDTO2.setLongitude(127.09416);
        locationDTO2.setLatitude(37.597466);

        List<Locations> locationList = new ArrayList<>();
        locationList.add(locationMapper.toLocations(locationDTO1));
        locationList.add(locationMapper.toLocations(locationDTO2));
        assertThat(locationList).hasSize(2);
        assertThat(locationList.get(0).getId()).isEqualTo(1);
        assertThat(locationList.get(1).getId()).isEqualTo(2);
        assertThat(locationList.get(0).getUser().getId()).isEqualTo(1);
        assertThat(locationList.get(1).getUser().getId()).isEqualTo(2);
    }


}