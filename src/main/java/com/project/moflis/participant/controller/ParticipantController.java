package com.project.moflis.participant.controller;

import com.project.moflis.global.security.model.CustomUserDetails;
import com.project.moflis.participant.command.ApplyParticipantCommand;
import com.project.moflis.participant.dto.ParticipantResponse;
import com.project.moflis.participant.enums.ParticipantStatus;
import com.project.moflis.participant.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public ResponseEntity<List<ParticipantResponse>> getParticipants(@RequestParam int postId) {
        List<ParticipantResponse> participants = participantService.getParticipants(postId);
        return ResponseEntity.ok(participants);
    }

    @PostMapping("/apply")
    public ResponseEntity<ParticipantResponse> applyParticipant(
            @RequestParam int postId, @AuthenticationPrincipal CustomUserDetails user) {
        int userId = user.getId();
        ApplyParticipantCommand command = new ApplyParticipantCommand(
                postId,
                ParticipantStatus.PENDING,
                LocalDateTime.now(),
                null
        );
        ParticipantResponse response = participantService.applyParticipant(command, userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/cancel")
    public ResponseEntity<String> cancelParticipation(@RequestParam int postId, @AuthenticationPrincipal CustomUserDetails user) {
        participantService.cancelParticipation(postId, user.getId());
        return ResponseEntity.ok("참여 신청이 취소되었습니다.");
    }

    @PatchMapping("{participantId}/approve")
    public ResponseEntity<String> approve(@PathVariable int participantId, @AuthenticationPrincipal CustomUserDetails user) {
        int userId = user.getId();
        participantService.approve(participantId, userId);
        return ResponseEntity.ok("참여 신청이 승인되었습니다.");
    }

    @PatchMapping("{participantId}/reject")
    public ResponseEntity<String> reject(@PathVariable int participantId, @AuthenticationPrincipal CustomUserDetails user) {
        int userId = user.getId();
        participantService.reject(participantId, userId);
        return ResponseEntity.ok("참여 신청이 거절 되었습니다.");
    }


}
