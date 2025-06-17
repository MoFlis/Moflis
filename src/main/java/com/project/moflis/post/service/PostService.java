package com.project.moflis.post.service;

import com.project.moflis.post.command.AddPostCommand;
import com.project.moflis.post.command.UpdatePostCommand;
import com.project.moflis.post.dto.PostResponse;
import com.project.moflis.post.dto.PostSearchCondition;
import com.project.moflis.post.dto.PostSliceResponse;
import com.project.moflis.post.entity.Post;
import com.project.moflis.post.enums.PostSort;
import com.project.moflis.post.enums.PostStatus;
import com.project.moflis.post.mapper.PostMapper;
import com.project.moflis.post.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    public PostSliceResponse getPostList(PostSort sortBy, String cursor, int size, PostSearchCondition searchCondition) {
        Pageable pageable = PageRequest.of(0, size);
        Slice<Post> postSlice = postRepository.findNextPostsBy(sortBy, cursor, pageable, searchCondition);
        List<PostResponse> content = PostMapper.INSTANCE.toPostResponseList(postSlice.getContent());

        LocalDateTime nextCursor = null;
        if (postSlice.hasNext() && !postSlice.getContent().isEmpty()) {
            nextCursor = postSlice.getContent().get(postSlice.getContent().size() - 1).getDate();
        }

        return new PostSliceResponse(content, postSlice.hasNext(), nextCursor);
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

    public Post getPostWithLockAndValidate(long postId, long userId) {
        Post post = postRepository.findByIdWithPessimisticLock(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));

        if (post.getUser().getId() == userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 글에는 신청할 수 없습니다.");
        }

        if (post.getParticipantLimit() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "모집 인원이 잘못 설정되어 있습니다.");
        }

        if (post.getStatus() == PostStatus.DELETED || post.getStatus() == PostStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 모집 완료 되었거나 삭제된 글입니다.");
        }

        return post;
    }

    public void verifyAndHandleCapacity(Post post, int currentCount) {
        if (currentCount >= post.getParticipantLimit()) {
            throw new IllegalStateException("참가 인원이 이미 가득 찼습니다.");
        }
        if (currentCount + 1 >= post.getParticipantLimit()) {
            post.complete();
        }
    }
}
