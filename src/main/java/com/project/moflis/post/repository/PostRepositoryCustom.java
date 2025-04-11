package com.project.moflis.post.repository;

import com.project.moflis.post.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findNextPostsByDate(String sortBy, Object cursor, int size);
}
