package com.project.moflis.dto.schedule;

import com.project.moflis.command.scheduls.UpdateSchedulsCommand;
import com.project.moflis.enums.SchedulesStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
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
                this.postId,
                this.groupPostId,
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