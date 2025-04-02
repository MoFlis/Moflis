package com.project.moflis.participant.command;

import com.project.moflis.participant.enums.ParticipantStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ApplyParticipantCommand {
    private int postId;
    private int userId;
    private ParticipantStatus status;
    private LocalDateTime joinDate;
    private LocalDateTime leaveDate;
}
