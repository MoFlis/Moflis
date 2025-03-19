package com.project.moflis.schedules.command;

import com.project.moflis.schedules.enums.SchedulesStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ScheduleUpdateCommand {
    LocalDate getScheduleDate();

    LocalDateTime getStartTime();

    LocalDateTime getEndTime();

    String getScheduleTitle();

    String getDescription();

    SchedulesStatus getSchedulesStatus();
}
