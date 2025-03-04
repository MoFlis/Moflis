package com.project.moflis.repository;

import com.project.moflis.profile.entity.Profile;
import com.project.moflis.profile.repository.ProfileRepository;
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
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @BeforeEach
    public void setUp() {
        profileRepository.deleteAll();
    }

    @Test
    public void testFindByUserId() {
        Profile profile = new Profile();
        User user = new User();
        user.setId(1);
        profile.setUser(user);
        profileRepository.save(profile);

        Profile result = profileRepository.findByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.getUser().getId());
    }
}
