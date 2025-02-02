package com.project.moflis.repository;

import com.project.moflis.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Profile findByUserId(Integer userId);

}
