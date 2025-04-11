package com.project.moflis.recurringschedules.command;

import com.project.moflis.recurringschedules.enums.RepeatType;
import com.project.moflis.recurringschedules.enums.SchedulesStatus;
import com.project.moflis.recurringschedules.vo.RecurringScheduleValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class UpdateRecurringSchedulesCommand {

    private final Long userId;
    private final RepeatType repeatType;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String description;
    private final SchedulesStatus status;

    public RecurringScheduleValues toValues() {
        return new RecurringScheduleValues(
                repeatType,
                startDate,
                endDate,
                startTime,
                endTime,
                description,
                status
        );
    }


}
