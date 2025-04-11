package com.project.moflis.post.repository;

import com.project.moflis.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.date < :cursor ORDER BY p.date DESC")
    Slice<Post> findNextPostsBy(String sortBy, LocalDateTime cursor, Pageable pageable);
}
