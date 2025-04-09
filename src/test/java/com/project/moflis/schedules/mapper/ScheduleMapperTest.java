package com.project.moflis.schedules.mapper;

import com.project.moflis.recurringschedules.entity.RecurringSchedule;
import com.project.moflis.schedules.command.AddSchedulsCommand;
import com.project.moflis.schedules.dto.schedules.ScheduleResponse;
import com.project.moflis.schedules.dto.schedules.ScheduleSummaryResponse;
import com.project.moflis.schedules.entity.Schedule;
import com.project.moflis.schedules.enums.SchedulesStatus;
import com.project.moflis.schedules.enums.SchedulesType;
import com.project.moflis.user.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleMapperTest {
    private final ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Test
    public void testToScheduleDto() {

        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDate localDateTime = LocalDate.from(LocalDateTime.now());

        Schedule schedule = getSchedule(localDateTime, endTime, startTime);

        ScheduleResponse scheduleResponseDTO = INSTANCE.toScheduleDto(schedule);

        assertThat(scheduleResponseDTO.getId()).isEqualTo(1);
        assertThat(scheduleResponseDTO.getStartTime()).isEqualTo(startTime);
        assertThat(scheduleResponseDTO.getEndTime()).isEqualTo(endTime);
        assertThat(scheduleResponseDTO.getSchedulesStatus()).isEqualTo(SchedulesStatus.INACTIVE);
        assertThat(scheduleResponseDTO.getDescription()).isEqualTo("test");
        assertThat(scheduleResponseDTO.getRecurringSchedulesId().getId()).isEqualTo(1);
        assertThat(scheduleResponseDTO.getScheduleTitle()).isEqualTo("test");

    }

    @Test
    public void testToScheduleSummaryResponse() {

        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDate localDateTime = LocalDate.from(LocalDateTime.now());

        Schedule schedule = getSchedule(localDateTime, endTime, startTime);

        ScheduleSummaryResponse scheduleResponseDTO = INSTANCE.toScheduleSummaryResponse(schedule);

        assertThat(scheduleResponseDTO.getId()).isEqualTo(1);
        assertThat(scheduleResponseDTO.getStartTime()).isEqualTo(startTime);
        assertThat(scheduleResponseDTO.getEndTime()).isEqualTo(endTime);
        assertThat(scheduleResponseDTO.getSchedulesStatus()).isEqualTo(SchedulesStatus.INACTIVE);
        assertThat(scheduleResponseDTO.getDescription()).isEqualTo("test");
        assertThat(scheduleResponseDTO.getRecurringSchedulesId().getId()).isEqualTo(1);
        assertThat(scheduleResponseDTO.getScheduleTitle()).isEqualTo("test");

    }

    private static Schedule getSchedule(LocalDate localDateTime, LocalDateTime endTime, LocalDateTime startTime) {
        Schedule schedule = new Schedule();

        User user = new User();
        user.setId(1);
        schedule.setUser(user);

        schedule.setId(1);
        schedule.setScheduleDate(localDateTime);
        schedule.setDescription("test");
        schedule.setEndTime(endTime);
        schedule.setSchedulesStatus(SchedulesStatus.INACTIVE);

        RecurringSchedule recurringSchedules = new RecurringSchedule();
        recurringSchedules.setId(1);

        schedule.setRecurringSchedule(recurringSchedules);
        schedule.setStartTime(startTime);
        schedule.setScheduleTitle("test");
        return schedule;
    }


    @Test
    public void testToSchedule() {

        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDate localDateTime = LocalDate.from(LocalDateTime.now());

        ScheduleResponse schedule = new ScheduleResponse();
        schedule.setId(1);
        schedule.setUserId(1);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setSchedulesStatus(SchedulesStatus.INACTIVE);
        schedule.setDescription("test");
        schedule.setScheduleDate(localDateTime);
        schedule.setScheduleTitle("test");
        schedule.setSchedulesType(SchedulesType.PERSONAL);

        Schedule scheduleDTO = INSTANCE.toSchedule(schedule);

        assertThat(scheduleDTO.getId()).isEqualTo(1);
        assertThat(scheduleDTO.getStartTime()).isEqualTo(startTime);
        assertThat(scheduleDTO.getEndTime()).isEqualTo(endTime);
        assertThat(scheduleDTO.getSchedulesStatus()).isEqualTo(SchedulesStatus.INACTIVE);
        assertThat(scheduleDTO.getDescription()).isEqualTo("test");
        assertThat(scheduleDTO.getScheduleTitle()).isEqualTo("test");
        assertThat(scheduleDTO.getSchedulesType()).isEqualTo(SchedulesType.PERSONAL);
        assertThat(schedule.getScheduleDate()).isEqualTo(localDateTime);
        assertThat(scheduleDTO.getUser().getId()).isEqualTo(1);

    }

    @Test
    public void toSchedule() {
        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDate localDateTime = LocalDate.from(LocalDateTime.now());

        AddSchedulsCommand command = new AddSchedulsCommand(
                1, 1, localDateTime, startTime, endTime,
                "test", "test", SchedulesStatus.INACTIVE
                , SchedulesType.PERSONAL
        );
        Schedule schedule = INSTANCE.toSchedule(command);

        assertThat(schedule.getUser().getId()).isEqualTo(1);
        assertThat(schedule.getScheduleDate()).isEqualTo(localDateTime);
        assertThat(schedule.getSchedulesStatus()).isEqualTo(SchedulesStatus.INACTIVE);
        assertThat(schedule.getDescription()).isEqualTo("test");
        assertThat(schedule.getScheduleTitle()).isEqualTo("test");
        assertThat(schedule.getSchedulesType()).isEqualTo(SchedulesType.PERSONAL);
        assertThat(schedule.getStartTime()).isEqualTo(startTime);
        assertThat(schedule.getEndTime()).isEqualTo(endTime);


    }


}