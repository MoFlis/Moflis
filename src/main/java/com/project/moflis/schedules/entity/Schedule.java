package com.project.moflis.schedules.entity;

import com.project.moflis.post.entity.Post;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.schedules.command.UpdateSchedulsCommand;
import com.project.moflis.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;

    @ManyToOne
    @JoinColumn(name = "group_post_id")
    private Post groupPostId;

    @ManyToOne
    @JoinColumn(name = "recurring_schedules_id")
    private RecurringSchedules recurringSchedulesId;

    @Column(name = "schedule_date")
    private LocalDate scheduleDate;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "schedule_title", nullable = false, length = 255)
    private String scheduleTitle;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedules_status", nullable = false, length = 50)
    private SchedulesStatus schedulesStatus;

    public void updateSchedule(UpdateSchedulsCommand command) {
        this.scheduleDate = command.getScheduleDate();
        this.startTime = command.getStartTime();
        this.endTime = command.getEndTime();
        this.scheduleTitle = command.getScheduleTitle();
        this.description = command.getDescription();
        this.schedulesStatus = command.getSchedulesStatus();
    }

    public boolean isOwnedBy(Integer userId) {
        return this.user.getId().equals(userId);
    }
}
