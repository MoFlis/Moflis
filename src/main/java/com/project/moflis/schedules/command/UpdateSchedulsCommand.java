package com.project.moflis.schedules.command;

import com.project.moflis.schedules.enums.SchedulesStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateSchedulsCommand {

    private final Integer scheduleId;
    private final Integer userId;
    private final Integer recurringSchedulesId;
    private final LocalDate scheduleDate;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String scheduleTitle;
    private final String description;
    private final SchedulesStatus schedulesStatus;
}
