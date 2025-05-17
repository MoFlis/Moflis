package com.project.moflis.review.dto.request;

public record ReviewUpdateRequest(
        int rating,
        String content
) {
}