package com.project.moflis.repository;


import com.project.moflis.entity.Profiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profiles, Integer> {

    Profiles findByUserId(Integer userId);
}
