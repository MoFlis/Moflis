package com.project.moflis.recurringschedules.entity;

import com.project.moflis.recurringschedules.enums.RepeatType;
import com.project.moflis.recurringschedules.enums.SchedulesStatus;
import com.project.moflis.recurringschedules.vo.RecurringScheduleValues;
import com.project.moflis.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "recurring_schedules")
@Getter
@Setter
public class RecurringSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SchedulesStatus status;

    public void update(RecurringScheduleValues command) {
        this.repeatType = command.getRepeatType();
        this.startDate = command.getStartDate();
        this.endDate = command.getEndDate();
        this.startTime = command.getStartTime();
        this.endTime = command.getEndTime();
        this.description = command.getDescription();
        this.status = command.getStatus();
    }

    public void deactivate() {
        if (this.status == SchedulesStatus.INACTIVE) {
            throw new IllegalStateException("이미 비활성화된 일정입니다.");
        }
        this.status = SchedulesStatus.INACTIVE;
    }
}
