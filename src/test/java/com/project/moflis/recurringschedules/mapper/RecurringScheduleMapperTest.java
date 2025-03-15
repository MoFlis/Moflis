package com.project.moflis.recurringschedules.mapper;

import com.project.moflis.recurringschedules.command.AddRecurringSchedulesCommand;
import com.project.moflis.recurringschedules.dto.RecurringScheduleResponse;
import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.recurringschedules.enums.RepeatType;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.user.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class RecurringScheduleMapperTest {

    private final RecurringScheduleMapper INSTANCE = Mappers.getMapper(RecurringScheduleMapper.class);

    @Test
    void testToRecurringScheduleResponse() {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.from(LocalDateTime.now());
        LocalTime endTime = LocalTime.now();
        LocalTime startTime = LocalTime.now();

        RecurringSchedules recurringSchedules = new RecurringSchedules();

        User user = new User();
        user.setId(1);

        recurringSchedules.setUser(user);
        recurringSchedules.setId(1);
        recurringSchedules.setDescription("test");
        recurringSchedules.setEndDate(endDate);
        recurringSchedules.setStartDate(startDate);
        recurringSchedules.setEndTime(endTime);
        recurringSchedules.setStartTime(startTime);
        recurringSchedules.setRepeatType(RepeatType.DAILY);

        RecurringScheduleResponse recurringScheduleResponse = INSTANCE.toRecurringScheduleResponse(recurringSchedules);

        assertThat(recurringScheduleResponse.getId()).isEqualTo(1);
        assertThat(recurringScheduleResponse.getDescription()).isEqualTo("test");
        assertThat(recurringScheduleResponse.getEndDate()).isEqualTo(endDate);
        assertThat(recurringScheduleResponse.getStartDate()).isEqualTo(startDate);
        assertThat(recurringScheduleResponse.getEndTime()).isEqualTo(endTime);
        assertThat(recurringScheduleResponse.getStartTime()).isEqualTo(startTime);
        assertThat(recurringScheduleResponse.getRepeatType()).isEqualTo(RepeatType.DAILY);

    }

    @Test
    void testToRecurringSchedule() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.from(LocalDateTime.now());
        LocalTime endTime = LocalTime.now();
        LocalTime startTime = LocalTime.now();

        AddRecurringSchedulesCommand command = new AddRecurringSchedulesCommand(
                1, 1, RepeatType.DAILY, startDate, endDate, startTime, endTime, "test", SchedulesStatus.ACTIVE
        );

        RecurringSchedules recurringSchedules = INSTANCE.toRecurringSchedule(command);

        assertThat(recurringSchedules.getId()).isEqualTo(1);
        assertThat(recurringSchedules.getUser().getId()).isEqualTo(1);
        assertThat(recurringSchedules.getDescription()).isEqualTo("test");
        assertThat(recurringSchedules.getEndDate()).isEqualTo(endDate);
        assertThat(recurringSchedules.getStartDate()).isEqualTo(startDate);
        assertThat(recurringSchedules.getEndTime()).isEqualTo(endTime);
        assertThat(recurringSchedules.getStartTime()).isEqualTo(startTime);
        assertThat(recurringSchedules.getRepeatType()).isEqualTo(RepeatType.DAILY);
    }

}