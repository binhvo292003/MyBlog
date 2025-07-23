package com.brianvo.myblog.controller;

import com.brianvo.myblog.domain.dto.response.PostResponse;
import com.brianvo.myblog.domain.entity.Post;
import com.brianvo.myblog.mapper.PostMapper;
import com.brianvo.myblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId){

        List<Post> allPosts = postService.getAllPosts(categoryId, tagId);
        List<PostResponse> postResponses = allPosts.stream().map(postMapper::toResponse).toList();

        return ResponseEntity.ok(postResponses);
    }
}
