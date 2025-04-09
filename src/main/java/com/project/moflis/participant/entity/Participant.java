package com.project.moflis.participant.entity;

import com.project.moflis.participant.enums.ParticipantStatus;
import com.project.moflis.post.entity.Post;
import com.project.moflis.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "participants")
@NoArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
}
