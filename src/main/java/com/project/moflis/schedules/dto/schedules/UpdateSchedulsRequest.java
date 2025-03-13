package com.project.moflis.schedules.dto.schedules;

import com.project.moflis.schedules.command.UpdateSchedulsCommand;
import com.project.moflis.schedules.enums.SchedulesStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class UpdateSchedulsRequest {

    private Integer userId;
    private Integer postId;
    private Integer groupPostId;
    private Integer recurringPostId;
    private LocalDate scheduleDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String scheduleTitle;
    private String description;
    private String schedulesStatus;

    public UpdateSchedulsCommand toCommand(Integer scheduleId) {
        return new UpdateSchedulsCommand(
                scheduleId,
                this.userId,
                this.recurringPostId,
                this.scheduleDate,
                this.startTime,
                this.endTime,
                this.scheduleTitle,
                this.description,
                SchedulesStatus.valueOf(this.schedulesStatus.toUpperCase()) // 변환 로직도 여기서 처리
        );
    }


}