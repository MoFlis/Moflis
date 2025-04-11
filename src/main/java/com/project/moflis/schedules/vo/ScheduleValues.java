package com.project.moflis.schedules.vo;

import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.enums.SchedulesType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleValues {

    private final LocalDate scheduleDate;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String scheduleTitle;
    private final String description;
    private final SchedulesStatus schedulesStatus;
    private final SchedulesType schedulesType;
    private final Long recurringSchedulesId;

    public ScheduleValues(LocalDate scheduleDate, LocalDateTime startTime, LocalDateTime endTime,
                          String scheduleTitle, String description,
                          SchedulesStatus schedulesStatus, SchedulesType schedulesType,
                          Long recurringSchedulesId) {
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduleTitle = scheduleTitle;
        this.description = description;
        this.schedulesStatus = schedulesStatus;
        this.schedulesType = schedulesType;
        this.recurringSchedulesId = recurringSchedulesId;
    }

}
