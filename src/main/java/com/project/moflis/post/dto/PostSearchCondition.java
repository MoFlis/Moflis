package com.project.moflis.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PostSearchCondition {
    private String keyword;
    private Long tagId;
    private LocalDate date;
}
