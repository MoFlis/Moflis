package com.project.moflis.schedules.dto.schedules;

import com.project.moflis.schedules.command.AddSchedulsCommand;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.enums.SchedulesType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class AddSchedulsRequest {

    private Long userId;
    private Long postId;
    private Long groupPostId;
    private Long recurringSchedulesId;
    private LocalDate scheduleDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String scheduleTitle;
    private String description;
    private String schedulesStatus;
    private String schedulesType;

    public AddSchedulsCommand toCommand() {
        return new AddSchedulsCommand(
                userId,
                recurringSchedulesId,
                scheduleDate,
                startTime,
                endTime,
                scheduleTitle,
                description,
                SchedulesStatus.valueOf(schedulesStatus.toUpperCase()),
                SchedulesType.valueOf(schedulesType.toUpperCase())
        );
    }

}
