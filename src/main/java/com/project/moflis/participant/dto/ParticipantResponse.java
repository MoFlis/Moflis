package com.project.moflis.participant.dto;

import com.project.moflis.participant.enums.ParticipantStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ParticipantResponse {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private ParticipantStatus status;
    private LocalDateTime joinDate;
    private LocalDateTime leaveDate;
}
