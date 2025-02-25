package com.project.moflis.mapper;

import com.project.moflis.command.scheduls.AddSchedulsCommand;
import com.project.moflis.command.scheduls.UpdateSchedulsCommand;
import com.project.moflis.dto.schedule.ScheduleResponse;
import com.project.moflis.dto.schedule.ScheduleSummaryResponse;
import com.project.moflis.entity.Post;
import com.project.moflis.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    @Mapping(source = "user.id", target = "userId")
    ScheduleResponse toScheduleDto(Schedule schedule);

    @Mapping(source = "user.id", target = "userId")
    ScheduleSummaryResponse toScheduleSummaryResponse(Schedule schedule);

    @Mapping(source = "userId", target = "user.id")
    Schedule toSchedule(ScheduleResponse scheduleResponseDTO);


    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "postId", target = "postId", qualifiedByName = "mapToPost")
    @Mapping(source = "groupPostId", target = "groupPostId", qualifiedByName = "mapToPost")
    Schedule toSchedule(AddSchedulsCommand command);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "postId", target = "postId", qualifiedByName = "mapToPost")
    @Mapping(source = "groupPostId", target = "groupPostId", qualifiedByName = "mapToPost")
    Schedule toSchedule(UpdateSchedulsCommand command);

    @Named("mapToPost")
    default Post mapToPost(Integer postId) {
        if (postId == null) return null;
        Post post = new Post();
        post.setId(postId); // Integer 값을 Post 객체로 변환
        return post;
    }

    List<ScheduleResponse> toScheduleDtoList(List<Schedule> scheduleList);

    List<Schedule> toSchedulesList(List<ScheduleResponse> scheduleDTOList);

}
