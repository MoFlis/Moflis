package com.project.moflis.schedules.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.schedules.command.ScheduleUpdateCommand;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.enums.SchedulesType;
import com.project.moflis.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_schedules_id")
    private RecurringSchedules recurringSchedules;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "schedules_type", nullable = false, length = 50)
    private SchedulesType schedulesType;

    public Schedule(User user, RecurringSchedules recurringSchedules, LocalDate scheduleDate,
                    LocalDateTime startTime, LocalDateTime endTime, String scheduleTitle,
                    String description, SchedulesStatus schedulesStatus, SchedulesType schedulesType) {
        this.user = user;
        this.recurringSchedules = recurringSchedules;
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.scheduleTitle = scheduleTitle;
        this.description = description;
        this.schedulesStatus = schedulesStatus;
        this.schedulesType = schedulesType;
    }

    public void update(ScheduleUpdateCommand command) {
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
