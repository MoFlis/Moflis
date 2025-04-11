package com.project.moflis.participant.mapper;

import com.project.moflis.participant.command.ApplyParticipantCommand;
import com.project.moflis.participant.dto.ParticipantApplyResponse;
import com.project.moflis.participant.dto.ParticipantListItem;
import com.project.moflis.participant.entity.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ParticipantMapper {
    ParticipantMapper INSTANCE = Mappers.getMapper(ParticipantMapper.class);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    ParticipantListItem toParticipantResponse(Participant participant);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    ParticipantApplyResponse toParticipantApplyResponse(Participant participant);


    @Mapping(source = "postId", target = "post.id")
    Participant toParticipant(ApplyParticipantCommand applyParticipantCommand);

    List<ParticipantListItem> toParticipantResponseList(List<Participant> participantList);

}
