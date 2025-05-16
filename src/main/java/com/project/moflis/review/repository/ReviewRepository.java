package com.project.moflis.review.repository;

import com.project.moflis.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByReviewerIdAndRevieweeIdAndPostId(Long reviewerId, Long revieweeId, Long postId);
}
