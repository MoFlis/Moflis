package com.project.moflis.repository;

import com.project.moflis.location.entity.Location;
import com.project.moflis.location.repository.LocationRepository;
import com.project.moflis.user.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    public void setUp() {
        locationRepository.deleteAll();
    }


    @Test
    void findByUserId() {
        Location location = new Location();
        User user = new User();
        user.setId(1);
        location.setLatitude(37.7749); // Example latitude
        location.setLongitude(-122.4194);
        locationRepository.save(location);

        Location result = locationRepository.findByUserId(user.getId());
        assertNotNull(result);
        assertEquals(user.getId(), result.getUserId());
    }

    @Test
    void existsByUserId() {
        Location location = new Location();
        location.setUserId(1);
        location.setLatitude(37.7749);
        location.setLongitude(-122.4194);
        locationRepository.save(location);

        boolean result = locationRepository.existsByUserId(location.getUserId());
        assertEquals(true, result);
    }
}