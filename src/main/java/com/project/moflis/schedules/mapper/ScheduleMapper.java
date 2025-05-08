package com.project.moflis.schedules.mapper;

import com.project.moflis.recurringschedules.entity.RecurringSchedule;
import com.project.moflis.schedules.command.AddSchedulsCommand;
import com.project.moflis.schedules.dto.schedules.ScheduleResponse;
import com.project.moflis.schedules.dto.schedules.ScheduleSummaryResponse;
import com.project.moflis.schedules.entity.Schedule;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "recurringSchedule", target = "recurringSchedulesId")
    ScheduleResponse toScheduleDto(Schedule schedule);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "recurringSchedule", target = "recurringSchedulesId")
    ScheduleSummaryResponse toScheduleSummaryResponse(Schedule schedule);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "recurringSchedulesId", target = "recurringSchedule")
    Schedule toSchedule(ScheduleResponse scheduleResponseDTO);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "recurringSchedulesId", target = "recurringSchedule", qualifiedByName = "mapToRecurringSchedules")
    Schedule toSchedule(AddSchedulsCommand command);

    @Named("mapToRecurringSchedules")
    default RecurringSchedule mapToRecurringSchedules(Long recurringScheduleId) {
        if (recurringScheduleId == null) return null;
        RecurringSchedule recurringSchedules = new RecurringSchedule();
        recurringSchedules.setId(recurringScheduleId);
        return recurringSchedules;
    }

    List<ScheduleResponse> toScheduleDtoList(List<Schedule> scheduleList);

    List<Schedule> toSchedulesList(List<ScheduleResponse> scheduleDTOList);

}
