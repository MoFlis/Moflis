package com.project.moflis.post.repository;

import com.project.moflis.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Query("SELECT p.participantLimit FROM Post p WHERE p.id = :postId")
    int findParticipantLimitByPostId(@Param("postId") int postId);
}
