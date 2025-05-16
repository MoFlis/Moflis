package com.project.moflis.participant.entity;

import com.project.moflis.participant.enums.ParticipantStatus;
import com.project.moflis.post.entity.Post;
import com.project.moflis.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "participants")
@Setter
@NoArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipantStatus status;

    @Column(name = "join_date")
    private LocalDateTime joinDate = LocalDateTime.now();

    @Column(name = "leave_date")
    private LocalDateTime leaveDate;

    public void cancel() {
        this.status = ParticipantStatus.CANCELED;
        this.leaveDate = LocalDateTime.now();
    }

    public void confirmed() {
        this.status = ParticipantStatus.CONFIRMED;
    }

    public void reject() {
        this.status = ParticipantStatus.REJECTED;
        this.leaveDate = LocalDateTime.now();
    }

    public void validatePending() {
        if (this.status != ParticipantStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 처리된 신청입니다.");
        }
    }

    public static Participant create(Post post, User user, ParticipantStatus status) {
        Participant participant = new Participant();
        participant.post = post;
        participant.user = user;
        participant.status = status;
        participant.joinDate = LocalDateTime.now();
        return participant;
    }
}
