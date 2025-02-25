package com.project.moflis.repository;

import com.project.moflis.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SchedulesRepository extends JpaRepository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {

}
