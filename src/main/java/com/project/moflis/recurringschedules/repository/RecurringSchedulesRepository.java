package com.project.moflis.recurringschedules.repository;

import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringSchedulesRepository extends JpaRepository<RecurringSchedules, Integer> {

}
