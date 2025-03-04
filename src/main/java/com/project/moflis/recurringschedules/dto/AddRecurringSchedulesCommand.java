package com.project.moflis.recurringschedules.dto;

import com.project.moflis.recurringschedules.enums.RepeatType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class AddRecurringSchedulesCommand {

    private final Integer id;
    private final Integer groupPostId;
    private final Integer userId;
    private final RepeatType repeatType;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String description;

    public AddRecurringSchedulesCommand(Integer id, Integer groupPostId, Integer userId, RepeatType repeatType, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String description) {
        this.id = id;
        this.groupPostId = groupPostId;
        this.userId = userId;
        this.repeatType = repeatType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }
}
