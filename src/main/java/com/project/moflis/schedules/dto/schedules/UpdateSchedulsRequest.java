package com.project.moflis.schedules.dto.schedules;

import com.project.moflis.schedules.command.UpdateSchedulsCommand;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.enums.SchedulesType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class UpdateSchedulsRequest {

    private Long userId;
    private Long recurringPostId;
    private LocalDate scheduleDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String scheduleTitle;
    private String description;
    private String schedulesStatus;
    private String schedulesType;

    public UpdateSchedulsCommand toCommand(Long scheduleId) {
        return new UpdateSchedulsCommand(
                scheduleId,
                this.userId,
                this.recurringPostId,
                this.scheduleDate,
                this.startTime,
                this.endTime,
                this.scheduleTitle,
                this.description,
                SchedulesStatus.valueOf(this.schedulesStatus.toUpperCase()),
                SchedulesType.valueOf(this.schedulesType.toUpperCase())
        );
    }


}