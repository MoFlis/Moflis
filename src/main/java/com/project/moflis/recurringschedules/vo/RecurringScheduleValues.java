package com.project.moflis.recurringschedules.vo;

import com.project.moflis.recurringschedules.enums.RepeatType;
import com.project.moflis.recurringschedules.enums.SchedulesStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class RecurringScheduleValues {

    private final RepeatType repeatType;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String description;
    private final SchedulesStatus status;

    public RecurringScheduleValues(RepeatType repeatType, LocalDate startDate, LocalDate endDate,
                                   LocalTime startTime, LocalTime endTime, String description, SchedulesStatus status) {
        this.repeatType = repeatType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.status = status;
    }
}
