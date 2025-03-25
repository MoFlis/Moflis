package com.project.moflis.schedules.repository;

import com.project.moflis.schedules.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SchedulesRepository extends JpaRepository<Schedule, Integer>, JpaSpecificationExecutor<Schedule> {

    List<Schedule> findByRecurringScheduleId(int recurringScheduleId);

    void deleteByRecurringScheduleId(int recurringScheduleId);
}
