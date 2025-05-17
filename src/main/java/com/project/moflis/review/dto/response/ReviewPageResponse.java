package com.project.moflis.review.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ReviewPageResponse {
    private List<ReviewResponse> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public ReviewPageResponse(Page<ReviewResponse> page) {
        this.content = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}
