package com.brianvo.myblog.mapper;

import com.brianvo.myblog.domain.PostStatus;
import com.brianvo.myblog.domain.dto.response.TagResponse;
import com.brianvo.myblog.domain.entity.Post;
import com.brianvo.myblog.domain.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    TagResponse toTagResponse(Tag tag);

    @Named("calculatePostCount")
    default Integer calculatePostCount(Set<Post> posts) {
        if (posts == null) {
            return 0;
        }

        return (int) posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }

}
