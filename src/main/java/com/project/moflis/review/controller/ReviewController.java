package com.project.moflis.review.controller;

import com.project.moflis.global.security.model.CustomUserDetails;
import com.project.moflis.review.dto.request.ReviewRequest;
import com.project.moflis.review.dto.request.ReviewUpdateRequest;
import com.project.moflis.review.dto.response.ReviewPageResponse;
import com.project.moflis.review.dto.response.ReviewResponse;
import com.project.moflis.review.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ReviewPageResponse> getReview(@PathVariable Long userId, @PageableDefault Pageable pageable) {
        Page<ReviewResponse> reviews = reviewService.getReviewsByRevieweeId(userId, pageable);
        return ResponseEntity.ok(new ReviewPageResponse(reviews));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@AuthenticationPrincipal CustomUserDetails user, ReviewRequest reviewRequest) {
        Long userId = user.getId();
        ReviewResponse response = reviewService.addReview(userId, reviewRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@AuthenticationPrincipal CustomUserDetails user,
                                                       @PathVariable Long reviewId,
                                                       ReviewUpdateRequest request) {
        Long currentUserId = user.getId();
        ReviewResponse updatedReview = reviewService.updateReview(currentUserId, reviewId, request);
        return ResponseEntity.ok(updatedReview);
    }


}
