package com.project.moflis.post.command;

import com.project.moflis.post.enums.PostStatus;
import com.project.moflis.post.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AddPostCommand {
    private final Long userId;
    private final String name;
    private final Long tagId;
    private final LocalDateTime date;
    private final String location;
    private final Long participantLimit;
    private final PostStatus status;
    private final PostType type;
    private final String content;

}
