package com.project.moflis.post.dto;

import com.project.moflis.post.command.UpdatePostCommand;
import com.project.moflis.post.enums.PostStatus;
import com.project.moflis.post.enums.PostType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdatePostRequest {
    private Integer postId;
    private Integer userId;
    private String name;
    private Integer tagId;
    private LocalDateTime date;
    private String location;
    private Integer participantLimit;
    private PostStatus status;
    private PostType type;
    private String content;

    public UpdatePostCommand toCommand() {
        return new UpdatePostCommand(
                this.postId,
                this.userId,
                this.name,
                this.tagId,
                this.date,
                this.location,
                this.participantLimit,
                this.status,
                this.type,
                this.content
        );
    }


}
