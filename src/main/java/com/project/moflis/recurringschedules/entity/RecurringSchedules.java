package com.project.moflis.recurringschedules.entity;

import com.project.moflis.post.entity.Post;
import com.project.moflis.recurringschedules.enums.RepeatType;
import com.project.moflis.recurringschedules.dto.AddRecurringSchedulesCommand;
import com.project.moflis.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "recurring_schedules")
@Getter
@Setter
public class RecurringSchedules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_post_id")
    private Post groupPost;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "repeat_type")
    @Enumerated(EnumType.STRING)
    private RepeatType repeatType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public void addRecurringSchedules(AddRecurringSchedulesCommand command, Post groupPost, User user) {
        this.id = command.getId();
        this.groupPost = groupPost;
        this.user = user;
        this.repeatType = command.getRepeatType();
        this.startDate = command.getStartDate();
        this.endDate = command.getEndDate();
        this.startTime = command.getStartTime();
        this.endTime = command.getEndTime();
        this.description = command.getDescription();

    }
}
