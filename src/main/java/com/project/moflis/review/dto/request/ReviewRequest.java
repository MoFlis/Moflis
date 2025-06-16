package com.project.moflis.review.dto.request;

public record ReviewRequest(
        Long postId,
        Long revieweeId,
        int rating,
        String content
) {
}