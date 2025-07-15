package com.brianvo.myblog.mapper;

import com.brianvo.myblog.domain.PostStatus;
import com.brianvo.myblog.domain.dto.request.CreateCategoryRequest;
import com.brianvo.myblog.domain.dto.response.CategoryResponse;
import com.brianvo.myblog.domain.entity.Category;
import com.brianvo.myblog.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryResponse toResponse(Category category);

    Category toEntity(CreateCategoryRequest createCategoryRequest);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts) {
        if (posts == null) {
            return 0;
        }
        return posts.stream()
                .filter(p -> PostStatus.PUBLISHED.equals(p.getStatus()))
                .count();
    }
}
