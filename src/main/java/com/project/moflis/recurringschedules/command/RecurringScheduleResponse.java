package com.project.moflis.recurringschedules.command;

import com.project.moflis.recurringschedules.enums.RepeatType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class RecurringScheduleResponse {

    private Integer id;
    private Integer userId;
    private RepeatType repeatType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
}
