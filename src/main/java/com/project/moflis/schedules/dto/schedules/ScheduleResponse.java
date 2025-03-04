package com.project.moflis.schedules.dto.schedules;

import com.project.moflis.post.entity.Post;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleResponse {
    private Integer id;
    private Integer userId;
    private Post postId;
    private Post groupPostId;
    private RecurringSchedules recurringSchedulesId;
    private LocalDate scheduleDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String scheduleTitle;
    private String description;
    private SchedulesStatus schedulesStatus;
}
