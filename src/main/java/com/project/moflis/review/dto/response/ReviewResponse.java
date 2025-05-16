package com.project.moflis.review.dto.response;

import com.project.moflis.review.enums.ReviewStatus;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long reviewerId,
        Long revieweeId,
        Long postId,
        int rating,
        String content,
        LocalDateTime writeDate,
        ReviewStatus status
) {
}