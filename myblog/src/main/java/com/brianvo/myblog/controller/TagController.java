package com.brianvo.myblog.controller;

import com.brianvo.myblog.domain.dto.request.CreateTagRequest;
import com.brianvo.myblog.domain.dto.response.TagResponse;
import com.brianvo.myblog.domain.entity.Tag;
import com.brianvo.myblog.mapper.TagMapper;
import com.brianvo.myblog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagMapper tagMapper;
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        List<Tag> tags = tagService.getTags();
        List<TagResponse> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();

        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTags(@RequestBody
                                                        CreateTagRequest createTagRequest) {
        System.out.println("tag: " + createTagRequest.getNames());
        List<Tag> tags = tagService.createTags(createTagRequest.getNames());
        List<TagResponse> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();
        return new ResponseEntity<>(
                tagResponses,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

}
