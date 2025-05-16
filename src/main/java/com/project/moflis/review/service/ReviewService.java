package com.project.moflis.review.service;

import com.project.moflis.participant.service.ParticipantService;
import com.project.moflis.post.entity.Post;
import com.project.moflis.post.service.PostService;
import com.project.moflis.review.dto.request.ReviewRequest;
import com.project.moflis.review.dto.response.ReviewResponse;
import com.project.moflis.review.entity.Review;
import com.project.moflis.review.mapper.ReviewMapper;
import com.project.moflis.review.repository.ReviewRepository;
import com.project.moflis.user.entity.User;
import com.project.moflis.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ParticipantService participantService;
    private final UserService userService;
    private final PostService postService;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, ParticipantService participantService, UserService userService, PostService postService, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.participantService = participantService;
        this.userService = userService;
        this.postService = postService;
        this.reviewMapper = reviewMapper;
    }

    public ReviewResponse addReview(Long reviewerId, ReviewRequest request) {
        if (reviewerId.equals(request.revieweeId())) {
            throw new RuntimeException("자기 자신에게는 리뷰를 작성할 수 없습니다.");
        }

        if (reviewRepository.existsByReviewerIdAndRevieweeIdAndPostId(reviewerId, request.revieweeId(), request.postId())) {
            throw new RuntimeException("이미 리뷰를 작성했습니다.");
        }

        if (!participantService.validateReviewerAndRevieweeAreParticipants(request.postId(), reviewerId, request.revieweeId())) {
            throw new RuntimeException("작성자와 대상자는 모두 해당 게시글에 참여해야 합니다.");
        }

        User reviewer = userService.getUserById(reviewerId);
        User reviewee = userService.getUserById(request.revieweeId());

        Post post = postService.getPostById(request.postId());

        Review review = Review.toEntity(reviewer, reviewee, post, request.rating(), request.content());
        return reviewMapper.toReviewResponse(reviewRepository.save(review));
    }
}
