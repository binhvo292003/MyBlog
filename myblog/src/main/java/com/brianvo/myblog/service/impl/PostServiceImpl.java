package com.brianvo.myblog.service.impl;

import com.brianvo.myblog.domain.PostStatus;
import com.brianvo.myblog.domain.entity.Category;
import com.brianvo.myblog.domain.entity.Post;
import com.brianvo.myblog.domain.entity.Tag;
import com.brianvo.myblog.repository.PostRepository;
import com.brianvo.myblog.service.CategoryService;
import com.brianvo.myblog.service.PostService;
import com.brianvo.myblog.service.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category);
        }

        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag);
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

}
