package com.project.moflis.post.entity;

import com.project.moflis.post.enums.PostStatus;
import com.project.moflis.post.enums.PostType;
import com.project.moflis.post.vo.UpdatePostValues;
import com.project.moflis.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "tag_id")
    private Integer tagId;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "location")
    private String location;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PostType type;

    @Column(name = "content")
    private String content;

    public void update(UpdatePostValues command) {
        this.tagId = command.getTagId();
        this.name = command.getName();
        this.date = command.getDate();
        this.location = command.getLocation();
        this.participantLimit = command.getParticipantLimit();
        this.status = command.getStatus();
        this.type = command.getType();
        this.content = command.getContent();
    }

    public void delete() {
        if (this.status == PostStatus.DELETED) {
            throw new IllegalStateException("이미 비활성화된 일정입니다.");
        }
        this.status = PostStatus.DELETED;
    }

    public void complete() {
        this.status = PostStatus.COMPLETED;
    }

}
