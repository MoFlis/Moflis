package com.project.moflis.recurringschedules.repository;

import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.schedules.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurringSchedulesRepository extends JpaRepository<RecurringSchedules, Integer> {

    @Query("SELECT r FROM RecurringSchedules r WHERE r.user.id = :userId")
    List<Schedule> findByUserId(@Param("userId") int userId);
}
