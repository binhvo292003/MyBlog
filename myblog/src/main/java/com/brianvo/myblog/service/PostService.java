package com.brianvo.myblog.service;

import com.brianvo.myblog.domain.entity.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
}
