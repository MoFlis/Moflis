package com.project.moflis.post.repository;

import com.project.moflis.post.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Post> findNextPostsByDate(String sortBy, Object cursor, int size) {

        String sortField = getSortField(sortBy);
        String orderBy = getOrderByClause(sortBy);

        String jpql = "SELECT p FROM Post p " +
                "WHERE (:cursor IS NULL OR p." + sortField + " < :cursor) " +
                "ORDER BY " + orderBy;

        TypedQuery<Post> query = em.createQuery(jpql, Post.class)
                .setMaxResults(size);

        if (cursor != null) {
            query.setParameter("cursor", cursor);
        } else {
            query.setParameter("cursor", null);
        }

        return query.getResultList();
    }

    private String getSortField(String sortBy) {
        return switch (sortBy) {
            case "views" -> "views";
            case "hit" -> "hit";
            case "participants" -> "participantCount";
            default -> "date";
        };
    }

    private String getOrderByClause(String sortBy) {
        return switch (sortBy) {
            case "views" -> "p.views DESC";
            case "hit" -> "p.hit DESC";
            case "participants" -> "p.participantCount DESC";
            default -> "p.date DESC";
        };
    }
}
