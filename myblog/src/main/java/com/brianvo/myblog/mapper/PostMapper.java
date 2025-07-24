package com.brianvo.myblog.mapper;

import com.brianvo.myblog.domain.dto.request.CreatePostRequest;
import com.brianvo.myblog.domain.dto.response.PostResponse;
import com.brianvo.myblog.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    PostResponse toResponse(Post post);

}
