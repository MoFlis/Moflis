package com.project.moflis.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostSliceResponse {
    private List<PostResponse> response;
    private boolean hasNextPage;
}
