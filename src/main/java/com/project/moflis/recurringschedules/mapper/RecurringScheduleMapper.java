package com.project.moflis.recurringschedules.mapper;

import com.project.moflis.recurringschedules.command.RecurringScheduleResponse;
import com.project.moflis.recurringschedules.dto.AddRecurringSchedulesCommand;
import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.schedules.dto.schedules.ScheduleResponse;
import com.project.moflis.schedules.entity.Schedule;
import com.project.moflis.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RecurringScheduleMapper {

    RecurringScheduleMapper INSTANCE = Mappers.getMapper(RecurringScheduleMapper.class);

    // Entity -> DTO 변환
    @Mapping(source = "user", target = "userId", qualifiedByName = "mapUserToId")
    RecurringScheduleResponse toRecurringScheduleResponse(RecurringSchedules recurringSchedules);

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapToUser")
    RecurringSchedules toRecurringSchedule(AddRecurringSchedulesCommand addRecurringSchedulesCommand);

    // 리스트 변환
    List<RecurringScheduleResponse> toRecurringScheduleDtoList(List<RecurringSchedules> recurringSchedulesList);

    List<ScheduleResponse> toSchedulesList(List<Schedule> schedules);

    @Named("mapUserToId")
    default Integer mapUserToId(User user) {
        return (user == null) ? null : user.getId();
    }

    @Named("mapToUser")
    default User mapToUser(Integer userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }
}
