package com.brianvo.myblog.controller;

import com.brianvo.myblog.domain.dto.request.CreatePostRequest;
import com.brianvo.myblog.domain.dto.response.PostResponse;
import com.brianvo.myblog.domain.entity.Post;
import com.brianvo.myblog.domain.entity.User;
import com.brianvo.myblog.mapper.PostMapper;
import com.brianvo.myblog.service.PostService;
import com.brianvo.myblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId){

        List<Post> allPosts = postService.getAllPosts(categoryId, tagId);
        List<PostResponse> postResponses = allPosts.stream().map(postMapper::toResponse).toList();

        return ResponseEntity.ok(postResponses);
    }

    @GetMapping("/drafts")
    public ResponseEntity<List<PostResponse>> getDrafts(@RequestAttribute UUID userId){
        User loggedInUser = userService.getUserById(userId);
        List<Post> drafts = postService.getDraftsPosts(loggedInUser);
        List<PostResponse> postResponses = drafts.stream().map(postMapper::toResponse).toList();
        return ResponseEntity.ok(postResponses);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestBody CreatePostRequest createPostRequest,
            @RequestAttribute UUID userId
            ){
        User loggedInUser = userService.getUserById(userId);
        Post createPost = postService.createPost(loggedInUser, createPostRequest);

        PostResponse response = postMapper.toResponse(createPost);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
