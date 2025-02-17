package com.project.moflis.repository;

import com.project.moflis.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface SchedulesRepository extends JpaRepository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {

    List<Schedule> findByUserId(int userId);

    Page<Schedule> findByUserIdAndScheduleDateBetween(int userId, LocalDate start, LocalDate end, Pageable pageable);

}
