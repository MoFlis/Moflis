package com.project.moflis.review.mapper;

import com.project.moflis.review.dto.response.ReviewResponse;
import com.project.moflis.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "reviewer.id", target = "reviewerId")
    @Mapping(source = "reviewee.id", target = "revieweeId")
    @Mapping(source = "post.id", target = "postId")
    ReviewResponse toReviewResponse(Review review);
}
