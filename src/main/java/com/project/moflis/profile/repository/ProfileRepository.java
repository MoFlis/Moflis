package com.project.moflis.profile.repository;

import com.project.moflis.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUserId(Long userId);

}
