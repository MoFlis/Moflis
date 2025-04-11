package com.project.moflis.schedules.dto.schedules;

import com.project.moflis.recurringschedules.entity.RecurringSchedule;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.enums.SchedulesType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleSummaryResponse {

    private Long id;
    private Long userId;
    private RecurringSchedule recurringSchedulesId;
    private LocalDate scheduleDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String scheduleTitle;
    private String description;
    private SchedulesStatus schedulesStatus;
    private SchedulesType schedulesType;

}
