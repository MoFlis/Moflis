package com.project.moflis.recurringschedules.command;

import com.project.moflis.recurringschedules.enums.RepeatType;
import com.project.moflis.schedules.enums.SchedulesStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public interface RecurringSchedulesUpdateCommand {
    Integer getUserId();

    RepeatType getRepeatType();

    LocalDate getStartDate();

    LocalDate getEndDate();

    LocalTime getStartTime();

    LocalTime getEndTime();

    String getDescription();

    SchedulesStatus getStatus();
}
