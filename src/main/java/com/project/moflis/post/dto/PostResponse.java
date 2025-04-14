package com.project.moflis.post.dto;

import com.project.moflis.post.enums.PostStatus;
import com.project.moflis.post.enums.PostType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {

    private Long id;
    private Long userId;
    private String name;
    private Long tagId;
    private LocalDateTime date;
    private String location;
    private int participantLimit;
    private PostStatus status;
    private PostType type;
    private String content;
    private int hit;
    private int likes;
}
