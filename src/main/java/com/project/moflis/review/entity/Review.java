package com.project.moflis.review.entity;

import com.project.moflis.post.entity.Post;
import com.project.moflis.review.enums.ReviewStatus;
import com.project.moflis.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewee_id", nullable = false)
    private User reviewee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @Column(nullable = false)
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "write_date", nullable = false)
    private LocalDateTime writeDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status = ReviewStatus.ACTIVE;

    public static Review toEntity(User reviewer, User reviewee, Post post, int rating, String content) {
        Review review = new Review();
        review.reviewer = reviewer;
        review.reviewee = reviewee;
        review.post = post;
        review.rating = rating;
        review.content = content;
        review.writeDate = LocalDateTime.now();
        review.status = ReviewStatus.ACTIVE;
        return review;
    }

    public void update(int rating, String content) {
        this.rating = rating;
        this.content = content;
    }

}