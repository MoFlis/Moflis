package com.project.moflis.review.controller;

import com.project.moflis.global.security.model.CustomUserDetails;
import com.project.moflis.review.dto.request.ReviewRequest;
import com.project.moflis.review.dto.response.ReviewResponse;
import com.project.moflis.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@AuthenticationPrincipal CustomUserDetails user,
                                                    ReviewRequest reviewRequest) {
        Long userId = user.getId();
        ReviewResponse response = reviewService.addReview(userId, reviewRequest);
        return ResponseEntity.ok(response);
    }
}
