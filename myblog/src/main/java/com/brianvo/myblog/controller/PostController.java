package com.brianvo.myblog.controller;

import com.brianvo.myblog.domain.dto.request.CreatePostRequest;
import com.brianvo.myblog.domain.dto.request.UpdatePostRequest;
import com.brianvo.myblog.domain.dto.response.PostResponse;
import com.brianvo.myblog.domain.entity.Post;
import com.brianvo.myblog.domain.entity.User;
import com.brianvo.myblog.mapper.PostMapper;
import com.brianvo.myblog.service.PostService;
import com.brianvo.myblog.service.UserService;
import jakarta.validation.Valid;
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

    @GetMapping(path = "/{id}" )
    public ResponseEntity<PostResponse> getPostById(@PathVariable UUID id){
        Post post = postService.getPostById(id);
        PostResponse response = postMapper.toResponse(post);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody CreatePostRequest createPostRequest,
            @RequestAttribute UUID userId
            ){
        User loggedInUser = userService.getUserById(userId);
        Post createPost = postService.createPost(loggedInUser, createPostRequest);

        PostResponse response = postMapper.toResponse(createPost);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}" )
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequest updatePostRequest
    ){
        Post updatePost = postService.updatePost(id, updatePostRequest);
        PostResponse response = postMapper.toResponse(updatePost);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public  ResponseEntity<Void> deletePost(@PathVariable UUID id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
