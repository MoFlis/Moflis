package com.project.moflis.entity;

import com.project.moflis.enums.PostStatus;
import com.project.moflis.enums.PostType;
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
