package com.project.moflis.dto.schedule;

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
    private Integer recurringPostId;
    private LocalDate scheduleDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String scheduleTitle;
    private String description;
    private String schedulesStatus;

}
