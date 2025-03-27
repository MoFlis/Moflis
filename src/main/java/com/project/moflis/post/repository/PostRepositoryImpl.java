package com.project.moflis.post.repository;

import com.project.moflis.post.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Post> findNextPostsByDate(LocalDateTime cursor, int size) {
        String jpql = "SELECT p FROM Post p " +
                "WHERE (:cursor IS NULL OR p.date < :cursor) " +
                "ORDER BY p.date DESC";

        return em.createQuery(jpql, Post.class)
                .setParameter("cursor", cursor)
                .setMaxResults(size)
                .getResultList();
    }
}
