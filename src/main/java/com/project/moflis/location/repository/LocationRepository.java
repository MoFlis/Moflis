package com.project.moflis.location.repository;

import com.project.moflis.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    Location findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);
}
