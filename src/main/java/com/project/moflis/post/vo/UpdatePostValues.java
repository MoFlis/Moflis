package com.project.moflis.post.vo;

import com.project.moflis.post.enums.PostStatus;
import com.project.moflis.post.enums.PostType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdatePostValues {

    private final String name;
    private final Long tagId;
    private final LocalDateTime date;
    private final String location;
    private final int participantLimit;
    private final PostStatus status;
    private final PostType type;
    private final String content;

    public UpdatePostValues(String name, Long tagId, LocalDateTime date, String location, int participantLimit, PostStatus status, PostType type, String content) {
        this.name = name;
        this.tagId = tagId;
        this.date = date;
        this.location = location;
        this.participantLimit = participantLimit;
        this.status = status;
        this.type = type;
        this.content = content;
    }
}
