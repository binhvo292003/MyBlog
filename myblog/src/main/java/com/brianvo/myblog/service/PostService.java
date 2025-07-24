package com.brianvo.myblog.service;

import com.brianvo.myblog.domain.dto.request.CreatePostRequest;
import com.brianvo.myblog.domain.dto.request.UpdatePostRequest;
import com.brianvo.myblog.domain.entity.Post;
import com.brianvo.myblog.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);

    Post getPostById(UUID id);

    List<Post> getDraftsPosts(User user);

    Post createPost(User user, CreatePostRequest createPostRequest);

    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
}
