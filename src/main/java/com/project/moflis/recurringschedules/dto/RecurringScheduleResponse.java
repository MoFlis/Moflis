package com.project.moflis.recurringschedules.dto;

import com.project.moflis.recurringschedules.enums.RepeatType;
import com.project.moflis.schedules.enums.SchedulesStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class RecurringScheduleResponse {

    private Long id;
    private Long userId;
    private RepeatType repeatType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private SchedulesStatus status;
}
