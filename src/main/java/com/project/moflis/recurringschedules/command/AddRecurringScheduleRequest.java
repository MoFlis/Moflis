package com.project.moflis.recurringschedules.command;

import com.project.moflis.recurringschedules.enums.RepeatType;
import com.project.moflis.recurringschedules.dto.AddRecurringSchedulesCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class AddRecurringScheduleRequest {

    private Integer id;
    private Integer groupPostId;
    private Integer userId;
    private RepeatType repeatType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

    public AddRecurringSchedulesCommand toCommand(AddRecurringScheduleRequest request) {
        return new AddRecurringSchedulesCommand(
                this.id,
                this.groupPostId,
                this.userId,
                this.repeatType,
                this.startDate,
                this.endDate,
                this.startTime,
                this.endTime,
                this.description
        );
    }

}
