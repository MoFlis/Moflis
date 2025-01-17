package com.project.moflis.repository;

import com.project.moflis.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Locations, Integer> {

    Locations findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);
}
