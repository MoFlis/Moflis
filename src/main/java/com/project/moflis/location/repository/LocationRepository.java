package com.project.moflis.location.repository;

import com.project.moflis.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
