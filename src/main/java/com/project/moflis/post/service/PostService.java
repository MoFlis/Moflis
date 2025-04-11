package com.project.moflis.post.service;

import com.project.moflis.post.command.AddPostCommand;
import com.project.moflis.post.command.UpdatePostCommand;
import com.project.moflis.post.dto.PostResponse;
import com.project.moflis.post.dto.PostSliceResponse;
import com.project.moflis.post.entity.Post;
import com.project.moflis.post.mapper.PostMapper;
import com.project.moflis.post.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostSliceResponse getPostList(LocalDateTime cursor, int size) {
        List<Post> posts = postRepository.findNextPostsByDate(cursor, size + 1);

        boolean hasNext = posts.size() > size;
        if (hasNext) {
            posts.remove(posts.size() - 1);
        }

        List<PostResponse> content = PostMapper.INSTANCE.toPostResponseList(posts);

        LocalDateTime nextCursor = null;
        if (hasNext && !posts.isEmpty()) {
            Post lastPost = posts.get(posts.size() - 1);
            nextCursor = lastPost.getDate();
        }
        return new PostSliceResponse(content, hasNext, nextCursor);
    }

    @Transactional
    public PostResponse addPost(AddPostCommand command) {
        return PostMapper.INSTANCE.toPostResponse(postRepository.save(PostMapper.INSTANCE.toPost(command)));
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("존재 하지 않는 postId: " + postId + "입니다"));
        return PostMapper.INSTANCE.toPostResponse(post);
    }

    @Transactional
    public PostResponse updatePost(long postId, UpdatePostCommand command) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 글입니다"));
        post.update(command.toValues());
        return PostMapper.INSTANCE.toPostResponse(postRepository.save(post));
    }

    @Transactional
    public PostResponse deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 글입니다"));
        post.delete();
        return PostMapper.INSTANCE.toPostResponse(postRepository.save(post));
    }


    public Post getPostForApplication(long postId, long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));

        post.validateNotWrittenBy(userId);
        post.validateRecruitableStatus();
        post.validateRecruitmentLimit();
        return post;
    }
}
