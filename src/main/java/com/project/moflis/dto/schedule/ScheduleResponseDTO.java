package com.project.moflis.dto.schedule;

import com.project.moflis.entity.Post;
import com.project.moflis.entity.RecurringMeeting;
import com.project.moflis.enums.SchedulesStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleResponseDTO {
    private Integer id;
    private Integer userId;
    private Post postId;
    private Post groupPostId;
    private RecurringMeeting recurringMeetingId;
    private LocalDate scheduleDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String scheduleTitle;
    private String description;
    private SchedulesStatus schedulesStatus;
}
