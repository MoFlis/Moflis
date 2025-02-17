package com.project.moflis.command.scheduls;

import com.project.moflis.enums.SchedulesStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateSchedulsCommand {

    private final Integer scheduleId;
    private final Integer userId;
    private final Integer postId;
    private final Integer groupPostId;
    private final Integer recurringPostId;
    private final LocalDate scheduleDate;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String scheduleTitle;
    private final String description;
    private final SchedulesStatus schedulesStatus;
}
