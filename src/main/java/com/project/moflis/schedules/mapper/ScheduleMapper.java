package com.project.moflis.schedules.mapper;

import com.project.moflis.post.entity.Post;
import com.project.moflis.recurringschedules.entity.RecurringSchedules;
import com.project.moflis.schedules.command.AddSchedulsCommand;
import com.project.moflis.schedules.command.UpdateSchedulsCommand;
import com.project.moflis.schedules.dto.schedules.ScheduleResponse;
import com.project.moflis.schedules.dto.schedules.ScheduleSummaryResponse;
import com.project.moflis.schedules.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "recurringSchedulesId", target = "recurringSchedulesId")
    ScheduleResponse toScheduleDto(Schedule schedule);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "recurringSchedulesId", target = "recurringSchedulesId")
    ScheduleSummaryResponse toScheduleSummaryResponse(Schedule schedule);

    @Mapping(source = "userId", target = "user.id")
    Schedule toSchedule(ScheduleResponse scheduleResponseDTO);


    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "postId", target = "postId", qualifiedByName = "mapToPost")
    @Mapping(source = "groupPostId", target = "groupPostId", qualifiedByName = "mapToPost")
    @Mapping(source = "recurringSchedulesId", target = "recurringSchedulesId", qualifiedByName = "mapToRecurringSchedules")
    Schedule toSchedule(AddSchedulsCommand command);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "postId", target = "postId", qualifiedByName = "mapToPost")
    @Mapping(source = "groupPostId", target = "groupPostId", qualifiedByName = "mapToPost")
    @Mapping(source = "recurringSchedulesId", target = "recurringSchedulesId", qualifiedByName = "mapToRecurringSchedules")
    Schedule toSchedule(UpdateSchedulsCommand command);

    @Named("mapToPost")
    default Post mapToPost(Integer postId) {
        if (postId == null) return null;
        Post post = new Post();
        post.setId(postId); // Integer 값을 Post 객체로 변환
        return post;
    }

    @Named("mapToRecurringSchedules")
    default RecurringSchedules mapToRecurringSchedules(Integer recurringScheduleId) {
        if (recurringScheduleId == null) return null;
        RecurringSchedules recurringSchedules = new RecurringSchedules();
        recurringSchedules.setId(recurringScheduleId);
        return recurringSchedules;
    }

    List<ScheduleResponse> toScheduleDtoList(List<Schedule> scheduleList);

    List<Schedule> toSchedulesList(List<ScheduleResponse> scheduleDTOList);

}
