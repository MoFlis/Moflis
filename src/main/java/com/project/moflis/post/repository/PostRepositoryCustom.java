package com.project.moflis.post.repository;

import com.project.moflis.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findNextPostsByDate(LocalDateTime cursor, int size);
}
