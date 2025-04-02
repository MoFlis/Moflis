package com.project.moflis.post.mapper;

import com.project.moflis.post.command.AddPostCommand;
import com.project.moflis.post.dto.PostResponse;
import com.project.moflis.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "userId", target = "user.id")
    Post toPost(AddPostCommand addPostCommand);

    @Mapping(source = "user.id", target = "userId")
    PostResponse toPostResponse(Post post);

    List<PostResponse> toPostResponseList(List<Post> postList);

}
