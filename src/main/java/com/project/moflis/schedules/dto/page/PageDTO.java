package com.project.moflis.schedules.dto.page;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageDTO<T> {
    private final List<T> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements; // ✅ totalElements 유지
    private final int totalPages;     // ✅ totalPages 유지
    private final boolean first;
    private final boolean last;

    // ✅ Page<T>를 유지하면서 변환하도록 수정
    public PageDTO(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements(); // ✅ totalElements 유지
        this.totalPages = page.getTotalPages();       // ✅ totalPages 유지
        this.first = page.isFirst();
        this.last = page.isLast();
    }
}
