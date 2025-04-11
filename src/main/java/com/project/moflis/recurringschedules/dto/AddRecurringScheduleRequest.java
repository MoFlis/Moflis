package com.project.moflis.recurringschedules.dto;

import com.project.moflis.recurringschedules.command.AddRecurringSchedulesCommand;
import com.project.moflis.recurringschedules.enums.RepeatType;
import com.project.moflis.schedules.enums.SchedulesStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AddRecurringScheduleRequest {

    private Long id;
    private Long userId;
    private RepeatType repeatType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private SchedulesStatus status;

    public AddRecurringSchedulesCommand toCommand(AddRecurringScheduleRequest request) {
        return new AddRecurringSchedulesCommand(
                this.id,
                this.userId,
                this.repeatType,
                this.startDate,
                this.endDate,
                this.startTime,
                this.endTime,
                this.description,
                SchedulesStatus.ACTIVE
        );
    }

}
