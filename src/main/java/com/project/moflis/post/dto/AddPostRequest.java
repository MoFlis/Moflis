package com.project.moflis.post.dto;

import com.project.moflis.post.command.AddPostCommand;
import com.project.moflis.post.enums.PostStatus;
import com.project.moflis.post.enums.PostType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddPostRequest {
    private Long postId;
    private Long userId;
    private String name;
    private Long tagId;
    private LocalDateTime date;
    private String location;
    private Long participantLimit;
    private String status;
    private String type;
    private String content;

    public AddPostCommand toCommand() {
        return new AddPostCommand(
                this.userId,
                this.name,
                this.tagId,
                this.date,
                this.location,
                this.participantLimit,
                PostStatus.valueOf(status.toUpperCase()),
                PostType.valueOf(type.toUpperCase()),
                this.content
        );
    }
}
