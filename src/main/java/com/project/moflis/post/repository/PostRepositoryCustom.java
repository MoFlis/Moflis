package com.project.moflis.post.repository;

import com.project.moflis.post.dto.PostSearchCondition;
import com.project.moflis.post.entity.Post;
import com.project.moflis.post.enums.PostSort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {
    Slice<Post> findNextPostsBy(PostSort sortBy, String cursor, Pageable pageable, PostSearchCondition condition);
}
