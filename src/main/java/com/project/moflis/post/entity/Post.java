package com.project.moflis.post.entity;

import com.project.moflis.post.enums.PostStatus;
import com.project.moflis.post.enums.PostType;
import com.project.moflis.profile.entity.Tags;
import com.project.moflis.user.entity.User;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tags tags;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "location")
    private String location;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "status")
    private PostStatus status;

    @Column(name = "type")
    private PostType type;

    @Column(name = "content")
    private String content;

}
