package com.project.moflis.recurringschedules.mapper;

import com.project.moflis.recurringschedules.command.AddRecurringSchedulesCommand;
import com.project.moflis.recurringschedules.command.UpdateRecurringSchedulesCommand;
import com.project.moflis.recurringschedules.dto.RecurringScheduleResponse;
import com.project.moflis.recurringschedules.entity.RecurringSchedule;
import com.project.moflis.schedules.dto.schedules.ScheduleResponse;
import com.project.moflis.schedules.entity.Schedule;
import com.project.moflis.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecurringScheduleMapper {

    RecurringScheduleMapper INSTANCE = Mappers.getMapper(RecurringScheduleMapper.class);

    // Entity -> DTO 변환
    @Mapping(source = "user", target = "userId", qualifiedByName = "mapUserToId")
    RecurringScheduleResponse toRecurringScheduleResponse(RecurringSchedule recurringSchedules);

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapToUser")
    RecurringSchedule toRecurringSchedule(AddRecurringSchedulesCommand addRecurringSchedulesCommand);

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapToUser")
    RecurringSchedule toRecurringSchedule(UpdateRecurringSchedulesCommand updateRecurringSchedulesCommand);

    // 리스트 변환
    List<RecurringScheduleResponse> toRecurringScheduleDtoList(List<RecurringSchedule> recurringSchedulesList);

    List<ScheduleResponse> toSchedulesList(List<Schedule> schedules);

    @Named("mapUserToId")
    default Long mapUserToId(User user) {
        return (user == null) ? null : user.getId();
    }

    @Named("mapToUser")
    default User mapToUser(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }
}
