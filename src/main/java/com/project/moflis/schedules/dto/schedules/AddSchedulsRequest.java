package com.project.moflis.schedules.dto.schedules;

import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.command.AddSchedulsCommand;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class AddSchedulsRequest {

    private Integer userId;
    private Integer postId;
    private Integer groupPostId;
    private Integer recurringSchedulesId;
    private LocalDate scheduleDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String scheduleTitle;
    private String description;
    private String schedulesStatus;

    public AddSchedulsCommand toCommand() {
        return new AddSchedulsCommand(
                userId,
                postId,
                groupPostId,
                recurringSchedulesId,
                scheduleDate,
                startTime,
                endTime,
                scheduleTitle,
                description,
                SchedulesStatus.valueOf(schedulesStatus.toUpperCase())  // 변환 로직을 내부에서 처리
        );
    }

}
