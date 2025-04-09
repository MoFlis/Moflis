package com.project.moflis.participant.service;

import com.project.moflis.participant.command.ApplyParticipantCommand;
import com.project.moflis.participant.dto.ParticipantApplyResponse;
import com.project.moflis.participant.dto.ParticipantResponse;
import com.project.moflis.participant.entity.Participant;
import com.project.moflis.participant.enums.ParticipantStatus;
import com.project.moflis.participant.repository.ParticipantRepository;
import com.project.moflis.post.entity.Post;
import com.project.moflis.post.enums.PostStatus;
import com.project.moflis.post.service.PostService;
import com.project.moflis.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private PostService postService;

    @InjectMocks
    private ParticipantService participantService;


    @Test
    void getParticipants() {
        int postId = 1;

        Participant pr1 = new Participant();
        pr1.setId(postId);
        pr1.setStatus(ParticipantStatus.PENDING);

        Participant pr2 = new Participant();
        pr2.setId(postId);
        pr2.setStatus(ParticipantStatus.CONFIRMED);

        List<Participant> fakeList = List.of(pr1, pr2);
        when(participantRepository.findByPostId(postId)).thenReturn(fakeList);

        List<ParticipantResponse> participants = participantService.getParticipants(postId);

        assertNotNull(participants);
        assertFalse(participants.isEmpty());
        assertEquals(fakeList.size(), participants.size());
        assertEquals(fakeList.get(0).getId(), participants.get(0).getId());
        assertEquals(fakeList.get(1).getId(), participants.get(1).getId());
        assertEquals(fakeList.get(0).getStatus(), participants.get(0).getStatus());
        assertEquals(fakeList.get(1).getStatus(), participants.get(1).getStatus());

    }

    @Test
    void applyParticipant_성공() {

        int postId = 1;
        int userId = 2;

        ApplyParticipantCommand command = new ApplyParticipantCommand(postId, ParticipantStatus.PENDING, LocalDateTime.now(), null);

        Post fakePost = new Post();
        fakePost.setParticipantLimit(3);

        when(postService.getPostForApplication(postId, userId)).thenReturn(fakePost);
        when(participantRepository.countByPostId(postId)).thenReturn(2);
        when(participantRepository.existsByPostIdAndUserId(postId, userId)).thenReturn(false);
        when(participantRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ParticipantApplyResponse response = participantService.applyParticipant(command, userId);

        assertNotNull(response);
        assertEquals(ParticipantStatus.PENDING, response.getStatus());
        assertEquals(postId, response.getPostId());
        assertEquals(PostStatus.COMPLETED, fakePost.getStatus());
    }

    @Test
    void applyParticipant_참여인원_초과시_예외발생() {
        int postId = 1;
        int userId = 2;

        ApplyParticipantCommand command = new ApplyParticipantCommand(postId, ParticipantStatus.PENDING, LocalDateTime.now(), null);
        Post fakePost = new Post();
        fakePost.setParticipantLimit(2);

        when(postService.getPostForApplication(postId, userId)).thenReturn(fakePost);
        when(participantRepository.countByPostId(postId)).thenReturn(2);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> participantService.applyParticipant(command, userId)
        );

        assertEquals("참가 인원이 이미 가득 찼습니다.", exception.getMessage());

    }

    @Test
    void applyParticipant_이미_신청한_사용자_예외발생() {
        int postId = 1;
        int userId = 2;

        ApplyParticipantCommand command = new ApplyParticipantCommand(postId, ParticipantStatus.PENDING, LocalDateTime.now(), null);
        Post fakePost = new Post();
        fakePost.setParticipantLimit(3);

        when(postService.getPostForApplication(postId, userId)).thenReturn(fakePost);
        when(participantRepository.countByPostId(postId)).thenReturn(2);
        when(participantRepository.existsByPostIdAndUserId(postId, userId)).thenReturn(true);
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> participantService.applyParticipant(command, userId)
        );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "이미 신청한 사용자입니다.");
    }

    @Test
    void applyPArticipant_이후_상태_변경_확인() {
        int postId = 1;
        int userId = 2;

        ApplyParticipantCommand command = new ApplyParticipantCommand(postId, ParticipantStatus.PENDING, LocalDateTime.now(), null);
        Post fakePost = new Post();
        fakePost.setParticipantLimit(3);
        when(postService.getPostForApplication(postId, userId)).thenReturn(fakePost);
        when(participantRepository.countByPostId(postId)).thenReturn(2);
        when(participantRepository.existsByPostIdAndUserId(postId, userId)).thenReturn(false);
        when(participantRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ParticipantApplyResponse response = participantService.applyParticipant(command, userId);

        assertNotNull(response);
        assertEquals(PostStatus.COMPLETED, fakePost.getStatus());

    }

    @Test
    void cancelParticipation_정상취소() {
        int postId = 1;
        int userId = 2;

        Participant fakeParticipant = new Participant();
        fakeParticipant.setStatus(ParticipantStatus.CONFIRMED);

        when(participantRepository.findByPostIdAndUserId(postId, userId))
                .thenReturn(Optional.of(fakeParticipant));

        participantService.cancelParticipation(postId, userId);

        assertEquals(ParticipantStatus.CANCELED, fakeParticipant.getStatus());
    }


    @Test
    void approve_정상승인() {

        int participantId = 1;
        int userId = 100;

        User postOwner = new User();
        postOwner.setId(userId);

        Post post = new Post();
        post.setUser(postOwner);

        Participant participant = new Participant();
        participant.setId(participantId);
        participant.setStatus(ParticipantStatus.PENDING);
        participant.setPost(post);

        when(participantRepository.findById(participantId)).thenReturn(Optional.of(participant));

        participantService.approve(participantId, userId);

        assertEquals(ParticipantStatus.CONFIRMED, participant.getStatus());
    }

    @Test
    void approve_작성자가아닌유저가승인하려할때_예외발생() {

        int participantId = 1;
        int ownerId = 100;
        int otherUserId = 200;

        User postOwner = new User();
        postOwner.setId(ownerId);

        Post post = new Post();
        post.setUser(postOwner);

        Participant participant = new Participant();
        participant.setId(participantId);
        participant.setStatus(ParticipantStatus.PENDING);
        participant.setPost(post);

        when(participantRepository.findById(participantId)).thenReturn(Optional.of(participant));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> participantService.approve(participantId, otherUserId)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("신청 승인 권한이 없습니다.", exception.getReason());
    }

    @Test
    void reject_작성자가아닌유저가거절하려할때_예외발생() {
        int participantId = 1;
        int userId = 100;
        int otherUserId = 200;

        User postOwner = new User();
        postOwner.setId(userId);
        Post post = new Post();
        post.setUser(postOwner);

        Participant participant = new Participant();
        participant.setId(participantId);
        participant.setStatus(ParticipantStatus.PENDING);
        participant.setPost(post);

        when(participantRepository.findById(participantId)).thenReturn(Optional.of(participant));

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> participantService.reject(participantId, otherUserId)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("신청 거절 권한이 없습니다.", exception.getReason());

    }

}