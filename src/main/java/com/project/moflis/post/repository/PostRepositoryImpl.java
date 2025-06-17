package com.project.moflis.post.repository;

import com.project.moflis.post.dto.PostSearchCondition;
import com.project.moflis.post.entity.Post;
import com.project.moflis.post.entity.QPost;
import com.project.moflis.post.enums.PostSort;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QPost post = QPost.post;

    public PostRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Slice<Post> findNextPostsBy(PostSort sortBy, String cursor, Pageable pageable, PostSearchCondition condition) {
        List<Post> content = queryFactory
                .select(post)
                .from(post)
                .where(
                        cursorCondition(sortBy, cursor),
                        keywordCondition(condition.getKeyword()),
                        tagIdCondition(condition.getTagId()),
                        dateCondition(condition.getDate())
                ).orderBy(createOrderSpecifier(post, sortBy))
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = content.size() > pageable.getPageSize();
        if (hasNext) {
            content.remove(content.size() - 1);
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression cursorCondition(PostSort sortBy, String cursor) {
        if (cursor == null) {
            return null;
        }

        return switch (sortBy) {
            case LIKES -> post.likes.lt(Long.parseLong(cursor));
            case HIT -> post.hit.lt(Long.parseLong(cursor));
            case DATE -> post.date.lt(LocalDateTime.parse(cursor));
        };
    }

    private BooleanExpression keywordCondition(String keyword) {
        if (keyword == null || keyword.isBlank()) return null;
        return post.name.containsIgnoreCase(keyword)
                .or(post.content.containsIgnoreCase(keyword));
    }

    private BooleanExpression tagIdCondition(Long tagId) {
        return tagId == null ? null : post.tagId.eq(tagId);
    }

    private BooleanExpression dateCondition(LocalDate date) {
        LocalDateTime now = LocalDateTime.now();
        if (date != null) {
            return post.date.between(date.atStartOfDay(), date.plusDays(1).atStartOfDay())
                    .and(post.date.goe(now));
        }
        return post.date.goe(now); // 기본은 현재 이후만
    }

    private OrderSpecifier<?> createOrderSpecifier(QPost post, PostSort sortType) {
        return switch (sortType) {
            case LIKES -> new OrderSpecifier<>(Order.DESC, post.likes);
            case HIT -> new OrderSpecifier<>(Order.DESC, post.hit);
            case DATE -> new OrderSpecifier<>(Order.DESC, post.date);
        };
    }
}
