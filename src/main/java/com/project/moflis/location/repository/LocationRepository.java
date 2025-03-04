package com.project.moflis.location.repository;

import com.project.moflis.location.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Locations, Integer> {

    Locations findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);
}
