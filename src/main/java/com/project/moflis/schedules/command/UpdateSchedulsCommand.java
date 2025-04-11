package com.project.moflis.schedules.command;

import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.enums.SchedulesType;
import com.project.moflis.schedules.vo.ScheduleValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateSchedulsCommand {

    private final Long scheduleId;
    private final Long userId;
    private final Long recurringSchedulesId;
    private final LocalDate scheduleDate;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String scheduleTitle;
    private final String description;
    private final SchedulesStatus schedulesStatus;
    private final SchedulesType schedulesType;

    public ScheduleValues toValues() {
        return new ScheduleValues(
                scheduleDate,
                startTime,
                endTime,
                scheduleTitle,
                description,
                schedulesStatus,
                schedulesType,
                recurringSchedulesId
        );
    }

}
