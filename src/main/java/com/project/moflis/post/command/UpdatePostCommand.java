package com.project.moflis.post.command;

import com.project.moflis.post.enums.PostStatus;
import com.project.moflis.post.enums.PostType;
import com.project.moflis.post.vo.UpdatePostValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdatePostCommand {
    private final Integer postId;
    private final Integer userId;
    private final String name;
    private final Integer tags;
    private final LocalDateTime date;
    private final String location;
    private final Integer participantLimit;
    private final PostStatus status;
    private final PostType type;
    private final String content;

    public UpdatePostValues toValues() {
        return new UpdatePostValues(
                name,
                tags,
                date,
                location,
                participantLimit,
                status,
                type,
                content
        );
    }

}
