package com.project.moflis.participant.service;

import com.project.moflis.participant.command.ApplyParticipantCommand;
import com.project.moflis.participant.dto.ParticipantApplyResponse;
import com.project.moflis.participant.dto.ParticipantListItem;
import com.project.moflis.participant.entity.Participant;
import com.project.moflis.participant.enums.ParticipantStatus;
import com.project.moflis.participant.mapper.ParticipantMapper;
import com.project.moflis.participant.repository.ParticipantRepository;
import com.project.moflis.post.entity.Post;
import com.project.moflis.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final PostService postService;

    public ParticipantService(ParticipantRepository participantRepository, PostService postService) {
        this.participantRepository = participantRepository;
        this.postService = postService;
    }

    public List<ParticipantListItem> getParticipants(long postId) {
        List<Participant> participants = participantRepository.findByPostId(postId);
        return ParticipantMapper.INSTANCE.toParticipantResponseList(participants);
    }

    @Transactional
    public ParticipantApplyResponse applyParticipant(ApplyParticipantCommand command, long userId) {
        Post post = postService.getPostForApplication(command.getPostId(), userId);
        int currentCount = participantRepository.countByPostId(command.getPostId());

        if (currentCount >= post.getParticipantLimit()) {
            throw new IllegalStateException("참가 인원이 이미 가득 찼습니다.");
        }

        boolean alreadyApplied = participantRepository.existsByPostIdAndUserId(command.getPostId(), userId);
        if (alreadyApplied) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 신청한 사용자입니다.");
        }

        Participant participant = ParticipantMapper.INSTANCE.toParticipant(command);

        if (currentCount + 1 >= post.getParticipantLimit()) {
            post.complete();
        }
        return ParticipantMapper.INSTANCE.toParticipantApplyResponse(participantRepository.save(participant));
    }

    @Transactional
    public void cancelParticipation(long postId, long userId) {
        Participant participant = participantRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "참여 정보가 존재하지 않습니다."));

        if (participant.getStatus() == ParticipantStatus.CANCELED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 취소된 신청 입니다.");
        }
        participant.cancel();
    }

    @Transactional
    public void approve(long participantId, long userId) {
        Participant participant = participantRepository.findById(participantId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "참여 정보가 없습니다."));
        Post post = participant.getPost();

        post.validateOwner(userId, "신청 승인 권한이 없습니다.");
        participant.validatePending();
        participant.confirmed();
    }

    @Transactional
    public void reject(long participantId, long userId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "참여 정보가 존재하지 않습니다."));
        Post post = participant.getPost();

        post.validateOwner(userId, "신청 거절 권한이 없습니다.");
        participant.reject();
    }
}
